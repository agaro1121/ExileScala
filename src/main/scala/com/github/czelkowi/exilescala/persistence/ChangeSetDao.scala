package com.github.czelkowi.exilescala.persistence

import java.util.concurrent.TimeUnit

import com.github.czelkowi.exilescala.converters.ChangeSetMongoConverter
import com.github.czelkowi.exilescala.models.ChangeSet
import com.github.czelkowi.exilescala.models.ChangeSet._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah._
import com.mongodb.{DBObject, WriteResult}

import scala.concurrent.duration.Duration

/**
  * @author : Corey
  * @since : 4/23/2017 1:55 PM
  */
object ChangeSetDao {

  private val uri = "mongodb://localhost:27017/"
  private val database = "public-stash-api"
  private val db: MongoDB = MongoClient(MongoClientURI(uri))(database)
  private val collection: MongoCollection = db("changeSets")

  def insert(changeSet: ChangeSet): WriteResult = {
    collection.insert(ChangeSetMongoConverter.convertToMongoObject(changeSet))
  }

  def replace(changeSet: ChangeSet): WriteResult = {
    collection.remove(MongoDBObject(ID -> changeSet._id))
    collection.insert(ChangeSetMongoConverter.convertToMongoObject(changeSet))
  }

  def findById(changeId: String): Option[ChangeSet] = {
    val query = MongoDBObject(ID -> changeId)
    convertFromMongo(collection.findOne(query))
  }

  def removeById(_id: String): WriteResult = {
    collection.remove(MongoDBObject(ID -> _id))
  }

  def getLatestChange: Option[ChangeSet] = {
    convertFromMongo(
      collection.findOne(
        MongoDBObject("_id" -> MongoDBObject("$exists" -> true)),
        MongoDBObject.empty, //No projection filter
        MongoDBObject("timestamp" -> -1)
      )
    )
  }

  private def convertFromMongo(dbObj: Option[DBObject]): Option[ChangeSet] = {
    dbObj match {
      case Some(x) => Some(ChangeSetMongoConverter.convertFromMongoObject(x))
      case None => None
    }
  }
}
