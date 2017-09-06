package kafka

import db.phantom.entity.Income
import org.joda.time.{DateTime, DateTimeZone}
import org.scalatest.{FlatSpec, Inspectors, Matchers, OptionValues}
import org.scalatest.concurrent.ScalaFutures
import play.api.libs.json.Json
import services.KafkaService


class KafkaServiceSpec extends FlatSpec with Matchers with Inspectors with ScalaFutures with OptionValues{

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
    rawFeatures=List(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15),
    levelUp=1.2732,
    correctionLevelUp=1.2732,
    levelDown=1.2732,
    correctionLevelDown=1.2732,
    prediction=0,
    label=0,
    createdAt=new DateTime(DateTimeZone.UTC)
  )

  "Send message to Kafka" should " must be successful" in {
    KafkaService.send(record.hashCode().toString, Json.toJson(record).toString)
  }

}
