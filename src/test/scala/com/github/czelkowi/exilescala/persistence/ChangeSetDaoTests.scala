package com.github.czelkowi.exilescala.persistence

import java.util.Date

import com.github.czelkowi.exilescala.models.ChangeSet
import org.junit.runner.RunWith
import org.scalatest.Matchers.{equal, _}
import org.scalatest._
import org.scalatest.junit.JUnitRunner

/**
  * @author : Corey
  * @since : 4/23/2017 2:01 PM
  */
@RunWith(classOf[JUnitRunner])
class ChangeSetDaoTests extends FunSpec {
  describe("save and find") {
    val changeSet = ChangeSet(
      _id = "integration-test",
      nextChangeId= "next-integration-test",
      timestamp = new Date()
    )
    it("should insert a changeSet") {
      val result = ChangeSetDao.insert(changeSet)
      assert(result.wasAcknowledged())
    }
    it("should find a changeSet by ID") {
      val found = ChangeSetDao.findById(changeSet._id)
      changeSet should equal(found.get)
    }
    it("should replace a changeSet by ID") {
      val newChangeSet = new ChangeSet(
        _id = "integration-test",
        nextChangeId= "new-integration-test",
        timestamp = new Date()
      )
      val result = ChangeSetDao.replace(newChangeSet)
      assert(result.wasAcknowledged())
      val found = ChangeSetDao.findById(changeSet._id)
      changeSet should not equal found.get
      newChangeSet should equal(found.get)
    }
    it("should remove a changeSet by ID") {
      val result = ChangeSetDao.removeById(changeSet._id)
      assert(result.wasAcknowledged())
    }
    it("should not find a changeSet by ID") {
      val found = ChangeSetDao.findById(changeSet._id)
      assert(found.isEmpty)
    }
  }
}
