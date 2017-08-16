package controllers

import javax.inject._

import db.phantom.entity.Income
import play.api.Logger
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc._
import services.{CassandraService, Config, KafkaService}

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

    Json.fromJson[Income](request.body) match {
      case JsSuccess(income, _) =>
        KafkaService.send(config.getKafkaBootstrapServers, config.getKafkaIncomeTopic, income.hashCode().toString, request.body.toString)
        CassandraService.addIncome(income)
        Logger.debug(s"Can't insert message:"+request.body.toString)
        Created(s"Can't insert message:"+request.body.toString)
      case JsError(errors) =>
        BadRequest("Could not build a registry from the json provided. " + errors.mkString)
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
