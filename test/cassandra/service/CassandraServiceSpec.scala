package cassandra.service

import cassandra.utils.CassandraSpec
import db.phantom.entity.Income
import org.joda.time.{DateTime, DateTimeZone}
import services.CassandraService


/**
  * Cassandra service unit test
  */
class CassandraServiceSpec  extends CassandraSpec {

  override def beforeAll(): Unit = {
    super.beforeAll()
    database.create()
  }

  //Record UUID using for tests
  val recordUUID=java.util.UUID.randomUUID

  //Income class ussed for tests
  val record=Income.apply(
     id=recordUUID,
     symbol="EURUSD",
     timeframe="1M",
     date="2017-08-18",
     time="11:13",
     open=1.2732,
     high=1.2732,
     low=1.2732,
     close=1.2732,
     rawFeatures=List(1,2,3,4,5,6,7,8,9,10),
     levelUp=1.2732,
     correctionLevelUp=1.2732,
     levelDown=1.2732,
     correctionLevelDown=1.2732,
     prediction=0,
     label=0,
     createdAt=new DateTime(DateTimeZone.UTC)
  )

  "Add Income record to Cassandra" should " must be successful" in {
     CassandraService.addIncome(record)
   }

  "Read income record from Cassandra and compare" should " must be successful" in {
    val recordRead = CassandraService.getIncome(recordUUID)

    whenReady(recordRead){ income =>
      income shouldBe defined
      Option(income.get) shouldEqual Option(record)
    }
  }

  "Get result set from cassandra" should " must be successful" in {
    val resultSet = CassandraService.getIncomes(1)
    whenReady(resultSet){ results =>
      results.isFullyFetched shouldBe true
    }
  }

  "Delete record from Cassandra " should "must be succesful" in {
    val recordDelete = CassandraService.delIncome(recordUUID)
    whenReady(recordDelete){ results =>
      results.isExhausted shouldBe true
    }
  }

}
