package com.github.czelkowi.exilescala.converters

import java.util.Date

import com.github.czelkowi.exilescala.models.ChangeSet
import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject

/**
  * @author : Corey
  * @since : 4/23/2017 1:48 PM
  */
object ChangeSetMongoConverter {
  def convertToMongoObject(changeSet: ChangeSet): DBObject = {
    val builder = MongoDBObject.newBuilder
    builder += ChangeSet.ID -> changeSet._id
    builder += ChangeSet.NEXT_CHANGE_ID -> changeSet.nextChangeId
    builder += ChangeSet.TIMESTAMP -> changeSet.timestamp
    builder.result()
  }

  def convertFromMongoObject(db: DBObject): ChangeSet = {
    ChangeSet(
      _id = db.get(ChangeSet.ID).asInstanceOf[String],
      nextChangeId = db.get(ChangeSet.NEXT_CHANGE_ID).asInstanceOf[String],
      timestamp = db.get(ChangeSet.TIMESTAMP).asInstanceOf[Date]
    )
  }
}
