package com.github.czelkowi.exilescala.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 * @author : Corey
 * @since : 4/27/2017 6:51 PM
 */
class PollingActor(
  val drivingActor: ActorRef,
  val processingActor: ActorRef
)(implicit val mat: ActorMaterializer) extends Actor {

  //  implicit val actorSystem = ActorSystem("ExileSystem")
  //  implicit val materializer = ActorMaterializer()

  //  val system = ActorSystem("ExileSystem")
  //  val drivingActor: ActorRef = system.actorOf(Props[DrivingActor], "DrivingActor")
  //  val processingActor: ActorRef = system.actorOf(Props[ProcessingActor], "ProcessingActor")

  val pathOfExileUrl = "http://www.pathofexile.com/api/public-stash-tabs"

  import context.system //gets implicitly passed into `Http()`

  override def receive: PartialFunction[Any, Unit] = {
    case -1 => requestData()
    case id: String => requestData(id)
    case unknown: AnyRef => drivingActor ! unknown
  }

  private def requestData(): Unit = {
    println(s"Requesting data from $pathOfExileUrl")
    onResponse(Http().singleRequest(HttpRequest(HttpMethods.GET, Uri(pathOfExileUrl))))
  }

  private def requestData(id: String): Unit = {
    println(s"Requesting data from $pathOfExileUrl?id=$id")
    onResponse(Http().singleRequest(HttpRequest(HttpMethods.GET, Uri(pathOfExileUrl).withQuery(Query("id" -> id)))))
  }

  private def onResponse(httpResponse: Future[HttpResponse]): Unit = {
    httpResponse.onComplete {
      case Success(response) => {
        response.status match {
          case StatusCodes.OK => processingActor ! response.entity.dataBytes.map(_.utf8String).runForeach(println)
          case _ => drivingActor ! new Exception(s"Inproper status code received: ${response.status}")
        }
      }
      case Failure(e) => drivingActor ! e
    }
  }

}
