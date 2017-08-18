package services
import java.util.UUID

import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.dsl.ResultSet
import db.phantom.database.LocalIncomeDatabase
import db.phantom.entity.Income

import scala.concurrent.{ExecutionContext, Future}

/**
  *Cassandra service object
  *
  */
object CassandraService {

  implicit val ex = ExecutionContext.Implicits.global
  implicit val session = LocalIncomeDatabase.session
  implicit val keySpace = KeySpace(LocalIncomeDatabase.keyspace.name)

  /**
    * Create Income tables (using for tests and first running)
    * @return
    */
  def createIncomeTables = LocalIncomeDatabase.create()

  /**
    * Add income information from Metatrader or another software
    * @param income
    * @return
    */
  def addIncome(income:Income) = {
      LocalIncomeDatabase.Incomes.store(income)
  }

  /**
    * Get income information from Metatrader or another software
    * @param id
    * @return
    */
  def getIncome(id: UUID): Future[Option[Income]] = {
     LocalIncomeDatabase.Incomes.getById(id)
  }

  /**
    * Get incomes (result set)
    * @param limit
    * @return
    */
  def getIncomes(limit:Int): Future[ResultSet] = {
    LocalIncomeDatabase.Incomes.getLimit(limit)
  }

  /**
    * Delete income information from Cassandra database
    * @param id
    * @return
    */
  def delIncome(id: UUID) = {
    LocalIncomeDatabase.Incomes.deleteById(id)
  }

}
