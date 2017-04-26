package com.github.czelkowi.exilescala.schedulers


import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * @author : Corey
  * @since : 4/25/2017 8:38 PM
  */
object FixedIntervalScheduler {


  def runAtFixedInterval(interval: Int, boolPromise: Future[Boolean]): Unit = {
    var continue = false
    do {
      val currentTimeMillis = System.currentTimeMillis()
      println(currentTimeMillis)

      boolPromise onComplete {
        case Success(status) =>
          println("Status was received, it was " + status)
          continue = true
        case Failure(exception) =>
          println("An exception occurred, so we must terminate the process.")
          continue = false
      }

      val timeElapsed = System.currentTimeMillis() - currentTimeMillis
      if (timeElapsed < interval)
        Thread.sleep(interval - timeElapsed)
    } while (continue)
  }

}
