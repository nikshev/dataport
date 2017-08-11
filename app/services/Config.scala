package services

/**
  * Class for config parsing
  * @param configuration
  */
class Config (configuration: play.api.Configuration) {

  /**
    * Get kafka bootstrap servers from config file
    * @return String server:port
    */
  def getKafkaBootstrapServers: String = {

    val serverField = configuration.getString("bootstrapServer")
    val server = serverField match {
      case None => "localhost"
      case Some(server) => server
    }

    val portField = configuration.getString("bootstrapServerPort")
    val port = portField match {
      case None => "9092"
      case Some(port) => port
    }

    server + ":" + port
  }

  /**
    * Get kafka income topic
    * @return income topic name
    */
  def getKafkaIncomeTopic: String = {

    val topic = configuration.getString("incomeTopic")
    topic match {
      case None => "incomeTopic"
      case Some(topic) => topic
    }
  }

}
