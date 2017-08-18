package cassandra.utils
import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.database.DatabaseProvider
import db.phantom.database.{IncomeDatabase, LocalIncomeDatabase}
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.ExecutionContext

/**
  * Use CassandraDbProvider and CassandraSpec for unit tests
  */
trait CassandraDbProvider extends DatabaseProvider[IncomeDatabase] {
  implicit val ex = ExecutionContext.Implicits.global
  override implicit val session = LocalIncomeDatabase.session
  implicit val keySpace = KeySpace(LocalIncomeDatabase.keyspace.name)

  override def database: IncomeDatabase = LocalIncomeDatabase
}

trait CassandraSpec extends FlatSpec
  with Matchers
  with Inspectors
  with ScalaFutures
  with OptionValues
  with BeforeAndAfterAll
  with CassandraDbProvider
