package com.github.czelkowi.exilescala

import com.github.czelkowi.exilescala.mongo.ChangeSetDao

/**
  * @author : Corey
  * @since : 4/23/2017 11:06 AM
  */
object ExileMain {

  def main() {
    val latestChange = ChangeSetDao.getLatestChange
    try {
      println(latestChange.get._id)
    } catch {
      case e: Exception => println("No Change Found...")
    }
  }

}
