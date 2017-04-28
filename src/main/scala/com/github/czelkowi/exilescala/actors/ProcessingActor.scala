package com.github.czelkowi.exilescala.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * @author : Corey
  * @since : 4/27/2017 6:52 PM
  */
class ProcessingActor extends Actor {

  override def receive: PartialFunction[Any, Unit] = {
    case data: String => processData(data)
    case AnyRef => println("Holy shit bad")
  }

  val system = ActorSystem("ExileSystem")
  val dataStoreActor: ActorRef = system.actorOf(Props[DataStoreActor], "DataStoreActor")

  def processData(data: String): Unit = {
    println(s"Processing: $data")
  }

}
