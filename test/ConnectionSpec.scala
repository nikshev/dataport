import org.scalatest.{FlatSpec, Matchers}
import services.CassandraService

/**
  *
  */
class ConnectionSpec  extends FlatSpec with Matchers {

  "Connection with Cassandra" should " must be successful" in {
     CassandraService.createIncomeTables
   }
}
