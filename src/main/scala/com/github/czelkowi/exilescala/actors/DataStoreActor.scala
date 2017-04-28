package com.github.czelkowi.exilescala.actors

import akka.actor.Actor

/**
  * @author : Corey
  * @since : 4/27/2017 6:51 PM
  */
class DataStoreActor extends Actor {

  override def receive: PartialFunction[Any, Unit] = {
    case "findLastPersistedChange" => { println("We did it Dora!") }
    case s: String => doWork(s)
    case AnyRef => println("Holy shit bad")
  }

  //  val system = ActorSystem("ExileSystem")
  //  val pollingActor: ActorRef = system.actorOf(Props[PollingActor], "PollingActor")

  def doWork(s: String): Unit = {
    println(s"$s Persisting.")
  }
}
