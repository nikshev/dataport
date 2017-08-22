package services

import java.util.Properties

import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * Kafka service
  */
object KafkaService  {

 private val config = ConfigFactory.load()
  /**
    * Send message to Kafka
    * @param key - message key
    * @param value - message
    * @return
    */
   def send(key:String, value:String) = {
    val  props = new Properties()
    val server =config.getString("kafka.bootstrapServer")+":"+config.getString("kafka.bootstrapServerPort")
    props.put("bootstrap.servers",server) //Docker return container id and you must add container id to hosts
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks", "all")
    props.put("retries", "3")
    props.put("linger.ms", "1")
    props.put("group.id", "income")

    val producer = new KafkaProducer[String, String](props)

    val record = new ProducerRecord(config.getString("kafka.incomeTopic"), "", value)
    producer.send(record)

    producer.close()
  }

}



