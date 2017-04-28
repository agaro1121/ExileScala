package com.github.czelkowi.exilescala

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.ActorMaterializer
import com.github.czelkowi.exilescala.actors.{DataStoreActor, DrivingActor, PollingActor, ProcessingActor}

/**
  * @author : Corey
  * @since : 4/23/2017 11:06 AM
  */
object ExileMain extends App {

  implicit val system = ActorSystem("ExileSystem")
  implicit val mat = ActorMaterializer()
  val dataStoreActor: ActorRef = system.actorOf(Props[DataStoreActor], "DataStoreActor")
  val drivingActor: ActorRef = system.actorOf(Props(new DrivingActor(dataStoreActor)), "DrivingActor")
  val processingActor: ActorRef = system.actorOf(Props(new ProcessingActor(dataStoreActor)), "ProcessingActor")
  val pollingActor: ActorRef = system.actorOf(Props(new PollingActor(drivingActor, processingActor)), "PollingActor")

  drivingActor ! DrivingActor.NegativeOne(-1, pollingActor)
  drivingActor ! DrivingActor.Id("string", pollingActor)

}
