package com.github.czelkowi.exilescala

import akka.actor.{ActorRef, ActorSystem, Props}
import com.github.czelkowi.exilescala.actors.DrivingActor

/**
  * @author : Corey
  * @since : 4/23/2017 11:06 AM
  */
object ExileMain extends App {

  val system = ActorSystem("ExileSystem")
  val drivingActor: ActorRef = system.actorOf(Props[DrivingActor], "DrivingActor")

  drivingActor ! -1

}
