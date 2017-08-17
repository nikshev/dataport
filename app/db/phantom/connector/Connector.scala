package db.phantom.connector

import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._

object Connector {
  private val config = ConfigFactory.load()

  private val hosts = config.getStringList("cassandra.hosts")
  private val keyspace = config.getString("cassandra.keyspace")

  lazy val connector: CassandraConnection = ContactPoints(hosts).keySpace(keyspace)

}
