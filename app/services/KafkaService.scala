package services


//import cakesolutions.kafka.KafkaProducer.Conf
//import cakesolutions.kafka.{KafkaProducer, KafkaProducerRecord}
//import org.apache.kafka.clients.producer._
import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

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
    * @return
    */
   def send(server:String, topic:String, key:String, value:String):Boolean = {

    val  props = new Properties()
    props.put("bootstrap.servers", server) //Docker return container id and you must add container id to hosts
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks", "all")
    props.put("retries", "3")
    props.put("linger.ms", "1")

    val producer = new KafkaProducer[String, String](props)

    val record = new ProducerRecord(topic, "", value)
    producer.send(record)

    producer.close()

    true
  }
}



