package controllers

import java.util.UUID
import javax.inject._

import db.phantom.entity.Income
import org.joda.time.DateTime
import play.api.Logger
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.mvc._
import services.{CassandraService, Config}

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
    val config = new Config(configuration)

    implicit val locationReads: Reads[Income] =(
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
      (__ \ "createdAt").read[DateTime].orElse(Reads.pure(new DateTime()))
      )(Income.apply _)

    Json.fromJson[Income](request.body) match {
      case JsSuccess(income, _) =>
        //KafkaService.send(config.getKafkaBootstrapServers, config.getKafkaIncomeTopic, income.hashCode().toString, request.body.toString)
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
