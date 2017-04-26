package com.github.czelkowi.exilescala

import com.github.czelkowi.exilescala.persistence.ChangeSetDao
import com.github.czelkowi.exilescala.schedulers.FixedIntervalScheduler

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author : Corey
  * @since : 4/23/2017 11:06 AM
  */
object ExileMain extends App {

  def process: Future[Boolean] = {
    Future {
      val latestChange = ChangeSetDao.getLatestChange
      if (latestChange.nonEmpty) true
      else false
    }
  }

  FixedIntervalScheduler.runAtFixedInterval(5000, process)

}
