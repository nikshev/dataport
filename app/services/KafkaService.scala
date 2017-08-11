package services
import javax.inject._

import cakesolutions.kafka.{KafkaProducer, KafkaProducerRecord}
import cakesolutions.kafka.KafkaProducer.Conf
import org.apache.kafka.common.serialization.StringSerializer

/**
  * Kafka service
  */
object KafkaService  {

  /**
    * Send message to Kafka
    * @param server - bootstarp server
    * @param topic - topic name
    * @param key - message key
    * @param value - message
    * @return unit
    */
  def send (server:String, topic:String, key:String, value:String) = {
    val producer = KafkaProducer(
      Conf(new StringSerializer(), new StringSerializer(), bootstrapServers = server)
    )
    val record = KafkaProducerRecord(topic, Some(key), value)
    producer.send(record)
  }

}



