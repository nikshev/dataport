package controllers

import javax.inject._

import db.phantom.connector.Connector
import models.Income
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc._
import play.api.Logger
import services.{Config, KafkaService}

import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle REST HTTP requests
 */
@Singleton
class RestController @Inject()(configuration: play.api.Configuration)(implicit exec: ExecutionContext) extends Controller {

  /**
    * Add information from metatrader to Kafka and Cassandra
    * @return
    */
  def add = Action.async(parse.json) { request =>
    val config = new Config(configuration)

    Json.fromJson[Income](request.body) match {
      case JsSuccess(income, _) =>
        val sendToKafkaFuture = Future {
          KafkaService.send(config.getKafkaBootstrapServers, config.getKafkaIncomeTopic, income.hashCode().toString, request.body.toString)
        }
        val sendToCassandraFuture = Future {
          true //TO-DO add Cassandra methods for save data
        }
        for {
          sendToKafka <- sendToKafkaFuture
          sendToCassandra <- sendToCassandraFuture
        } yield {
           if (sendToKafka && sendToKafka == sendToCassandra) {
             Logger.debug(s"Successfully insert message:"+request.body.toString)
             Created(s"Successfully insert message:"+request.body.toString)
           } else {
             Logger.debug(s"Can't insert message:"+request.body.toString)
             Created(s"Can't insert message:"+request.body.toString)
           }

        }
      case JsError(errors) =>
        Future.successful(BadRequest("Could not build a registry from the json provided. " + errors.mkString))
    }
  }

  /**
    * Get prediction from Cassandra
    * @return
    */
  def prediction = Action {
    val conn = Connector.connector
    Ok("Prediction method")
  }


}
