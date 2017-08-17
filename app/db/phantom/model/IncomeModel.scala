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
    object symbol extends StringColumn
    object timeframe extends StringColumn
    object date extends StringColumn
    object time extends StringColumn
    object open extends DoubleColumn
    object high extends DoubleColumn
    object low extends DoubleColumn
    object close extends DoubleColumn
    object indicators extends ListColumn[Double]
    object levelUp extends DoubleColumn
    object correctionLevelUp extends DoubleColumn
    object levelDown extends DoubleColumn
    object correctionLevelDown extends DoubleColumn
    object prediction extends IntColumn
    object label extends IntColumn
    object createdAt extends DateTimeColumn


    def store(income: Income): Future[ResultSet] = {
      insert
        .value(_.id, income.id)
        .value(_.symbol, income.symbol)
        .value(_.timeframe, income.timeframe)
        .value(_.date, income.date)
        .value(_.time, income.time)
        .value(_.open, income.open)
        .value(_.high, income.high)
        .value(_.low, income.low)
        .value(_.close, income.close)
        .value(_.indicators, income.indicators)
        .value(_.levelUp, income.levelUp)
        .value(_.correctionLevelUp, income.correctionLevelUp)
        .value(_.levelDown, income.levelDown)
        .value(_.correctionLevelDown, income.correctionLevelDown)
        .value(_.createdAt, income.createdAt)
        .future()
    }

    def getById(id: UUID): Future[Option[Income]] = {
      select.where(_.id eqs id).one()
    }

    def getLimit(limit: Int): Future[ResultSet] = {
      select.limit(limit).future()
    }

}
