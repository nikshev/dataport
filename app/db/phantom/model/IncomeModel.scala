package db.phantom.model

import com.outworkers.phantom.dsl._
import db.phantom.entity.Income

import scala.concurrent.Future

/**
* Create the Cassandra representation of the Income table
*/
abstract class IncomeModel extends CassandraTable[IncomeModel, Income] with RootConnector{

    override def tableName: String = "income"

    object id extends UUIDColumn with PartitionKey

    object date extends StringColumn
    object time extends StringColumn
    object open extends DoubleColumn
    object high extends DoubleColumn
    object low extends DoubleColumn
    object close extends DoubleColumn


    def store(income: Income): Future[ResultSet] = {
      insert
        .value(_.id, income.id)
        .value(_.date, income.date)
        .future()
    }

    def getById(id: UUID): Future[Option[Income]] = {
      select.where(_.id eqs id).one()
    }

    def getLimit(limit: Int): Future[ResultSet] = {
      select.limit(limit).future()
    }

}
