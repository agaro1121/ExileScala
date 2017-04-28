package com.github.czelkowi.exilescala.actors

import akka.actor.{Actor, ActorRef}


object DrivingActor {
  case class Id(id: String, pollingActor: ActorRef)
}

class DrivingActor(
  val dataStoreActor: ActorRef,
  val pollingActor: ActorRef
) extends Actor {

  import DrivingActor._

  override def receive: PartialFunction[Any, Unit] = {
    case -1 => {
      println("Invoking the PollingActor with no ID.")
      pollingActor ! -1
    }
    case Id(id, pollingActor) => {
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
