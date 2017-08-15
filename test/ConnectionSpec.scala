import db.phantom.connector.Connector
import org.scalatest.{FlatSpec, Matchers}

/**
  *
  */
class ConnectionSpec  extends FlatSpec with Matchers {

  "Connection with Cassandra" should " must be successful" in {
      val conn = Connector.connector
      println(conn.name)
      println(conn.cassandraVersion)
   }
}
