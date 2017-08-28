package controllers

import java.util.UUID
import javax.inject._

import db.phantom.entity.Income
import org.joda.time.{DateTime, DateTimeZone}
import play.api.Logger
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.mvc._
import services.{CassandraService, KafkaService}

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle REST HTTP requests
 */
@Singleton
class RestController @Inject()(configuration: play.api.Configuration)(implicit exec: ExecutionContext) extends Controller {

  /**
    * Add information from metatrader to Kafka and Cassandra
    * @return
    */
  def add = Action(parse.json) { request =>

    implicit val incomeReads: Reads[Income] =(
      (__ \ "id").read[UUID].orElse(Reads.pure(java.util.UUID.randomUUID)) and
      (__ \ "symbol").read[String] and
      (__ \ "timeframe").read[String] and
      (__ \ "date").read[String] and
      (__ \ "time").read[String] and
      (__ \ "open").read[Double] and
      (__ \ "high").read[Double] and
      (__ \ "low").read[Double] and
      (__ \ "close").read[Double] and
      (__ \ "indicators").read[List[Double]] and
      (__ \ "levelUp").read[Double] and
      (__ \ "correctionLevelUp").read[Double] and
      (__ \ "levelDown").read[Double] and
      (__ \ "correctionLevelDown").read[Double] and
      (__ \ "prediction").read[Int].orElse(Reads.pure(0)) and
      (__ \ "label").read[Int].orElse(Reads.pure(0)) and
      (__ \ "createdAt").read[DateTime].orElse(Reads.pure(new DateTime(DateTimeZone.UTC)))
      )(Income.apply _)

    implicit val incomeWrites = new Writes[Income] {
      def writes(income: Income) = Json.obj(
        "id"-> income.id,
        "symbol"->income.symbol,
        "timeframe"->income.timeframe,
        "date"->income.date,
        "time"->income.time,
        "open"->income.open,
        "high"->income.high,
        "low"->income.low,
        "close"->income.close,
        "rawFeatures"->income.rawFeatures,
        "levelUp"->income.levelUp,
        "correctionLevelUp"->income.correctionLevelUp,
        "levelDown"->income.levelDown,
        "correctionLevelDown"->income.correctionLevelDown,
        "prediction"->income.prediction,
        "label"->income.label,
        "createdAt"-> income.createdAt
      )
    }

    Json.fromJson[Income](request.body) match {
      case JsSuccess(income, _) =>
        KafkaService.send(income.hashCode().toString, Json.toJson(income).toString)
        CassandraService.addIncome(income)
        Logger.debug(s"Message processed:"+request.body.toString)
        Created(s"Message processed:"+request.body.toString)
      case JsError(errors) =>
        BadRequest("Could not build a income from the provided json:" + errors.mkString)
    }
  }

  /**
    * Get prediction from Cassandra
    * @return
    */
  def prediction = Action {
    Ok("Prediction method")
  }


}
