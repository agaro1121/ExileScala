package com.github.czelkowi.exilescala.mongo

import com.github.czelkowi.exilescala.converters.ChangeSetMongoConverter
import com.github.czelkowi.exilescala.model.ChangeSet
import com.github.czelkowi.exilescala.model.ChangeSet._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.{MongoClient, MongoClientURI, MongoCollection, MongoDB}
import com.mongodb.{DBObject, WriteResult}

/**
  * @author : Corey
  * @since : 4/23/2017 1:55 PM
  */
object ChangeSetDao {
  private val uri = """mongodb://localhost:27017/"""
  val db: MongoDB = MongoClient(MongoClientURI(uri))( """public-stash-api""")
  val collection: MongoCollection = db("changeSets")

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
    convertFromMongo(collection
      .find(MongoDBObject("_id" -> MongoDBObject("$exists" -> true)))
      .sort(MongoDBObject("timestamp" -> -1))
      .limit(1)
      .toList
      .headOption)
  }

  private def convertFromMongo(dbObj: Option[DBObject]): Option[ChangeSet] = {
    dbObj match {
      case Some(x) => Some(ChangeSetMongoConverter.convertFromMongoObject(x))
      case None => None
    }
  }
}
