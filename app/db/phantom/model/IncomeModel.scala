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
    object symbol extends StringColumn with PartitionKey
    object timeframe extends StringColumn
    object date extends StringColumn
    object time extends StringColumn
    object open extends DoubleColumn
    object high extends DoubleColumn
    object low extends DoubleColumn
    object close extends DoubleColumn
    object rawFeatures extends ListColumn[Double]
    object levelUp extends DoubleColumn
    object correctionLevelUp extends DoubleColumn
    object levelDown extends DoubleColumn
    object correctionLevelDown extends DoubleColumn
    object prediction extends OptionalIntColumn
    object label extends OptionalIntColumn
    object createdAt extends DateTimeColumn with PartitionKey

    /**
      * Store information to the Cassandra database
      * @param income
      * @return
      */
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
        .value(_.rawFeatures, income.rawFeatures)
        .value(_.levelUp, income.levelUp)
        .value(_.correctionLevelUp, income.correctionLevelUp)
        .value(_.levelDown, income.levelDown)
        .value(_.correctionLevelDown, income.correctionLevelDown)
        .value(_.createdAt, income.createdAt)
        .future()
    }

    /**
      * Get one row by ID
      * @param id
      * @return
      */
    def getById(id: UUID): Future[Option[Income]] = {
      select.where(_.id eqs id).one()
    }

    /**
      * Get row set with limit
      * @param limit
      * @return
      */
    def getLimit(limit: Int): Future[ResultSet] = {
      select.limit(limit).future()
    }

    /**
      * Overriden method fromRow
      * @param row
      * @return
      */
    override def fromRow(row: Row): Income = {
        Income(
            id(row),
            symbol(row),
            timeframe(row),
            date(row),
            time(row),
            open(row),
            high(row),
            low(row),
            close(row),
            rawFeatures(row),
            levelUp(row),
            correctionLevelUp(row),
            levelDown(row),
            correctionLevelDown(row),
            prediction(row).getOrElse(0),
            label(row).getOrElse(0),
            createdAt(row))
    }

    /**
      * Delete row by ID
      * @param id
      * @return
      */
    def deleteById(id: UUID): Future[ResultSet] = {
        delete
          .where(_.id eqs id)
          .consistencyLevel_=(ConsistencyLevel.ONE)
          .future()
    }

    /**
      * Get rows by symbol which has created at greater than timestamp in params
      * @param symbol
      * @param timestamp
      * @return
      */
    def getBySymbol(symbol:String, timestamp:DateTime): Future[ResultSet] = {
        /**
          * TO-DO add keys for multi user support
          */
        select.where(_.symbol eqs symbol).and(_.createdAt > timestamp).future
    }
}
