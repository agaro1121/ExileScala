package com.github.czelkowi.exilescala.model

import java.util.Date

/**
  * @author : Corey
  * @since : 4/23/2017 1:44 PM
  */
case class ChangeSet(
                      _id: String,
                      nextChangeId: String,
                      timestamp: Date
                    )

object ChangeSet {
  val ID = "_id"
  val NEXT_CHANGE_ID = "next_change_id"
  val TIMESTAMP = "timestamp"
}