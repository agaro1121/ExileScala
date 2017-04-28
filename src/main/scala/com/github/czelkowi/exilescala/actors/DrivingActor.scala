package com.github.czelkowi.exilescala.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
 * @author : Corey
 * @since : 4/27/2017 7:30 PM
 */
class DrivingActor(
  val dataStoreActor: ActorRef,
  val pollingActor: ActorRef
) extends Actor {

  override def receive: PartialFunction[Any, Unit] = {
    case -1 => {
      println("Invoking the PollingActor with no ID.")
      pollingActor ! -1
    }
    case id: String => {
      println(s"Invoking the PollingActor with ID $id")
      pollingActor ! id
    }
    case e: Exception => {
      println(s"An exception was caught: ${e.getMessage}")
      println("Proceeding to sleep for 5s, and then restarting.")
      Thread.sleep(5000)
      self ! "findLastPersistedChange"
    }
  }

}
