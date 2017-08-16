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
    object i0 extends DoubleColumn
    object i1 extends DoubleColumn
    object i2 extends DoubleColumn
    object i3 extends DoubleColumn
    object i4 extends DoubleColumn
    object i5 extends DoubleColumn
    object i6 extends DoubleColumn
    object i7 extends DoubleColumn
    object i8 extends DoubleColumn
    object i9 extends DoubleColumn
    object i10 extends DoubleColumn
    object i11 extends DoubleColumn
    object i12 extends DoubleColumn
    object i13 extends DoubleColumn
    object i14 extends DoubleColumn
    object i15 extends DoubleColumn
    object i16 extends DoubleColumn
    object i17 extends DoubleColumn
    object i18 extends DoubleColumn
    object i19 extends DoubleColumn
    object i20 extends DoubleColumn
    object levelUp extends DoubleColumn
    object correctionLevelUp extends DoubleColumn
    object levelDown extends DoubleColumn
    object correctionLevelDown extends DoubleColumn
    object prediction extends IntColumn
    object label extends IntColumn
    object created_at extends DateTimeColumn


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
