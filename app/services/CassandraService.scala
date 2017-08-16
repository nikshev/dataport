package services

import com.outworkers.phantom.connectors.KeySpace
import db.phantom.database.LocalIncomeDatabase
import db.phantom.entity.Income

import scala.concurrent.ExecutionContext

object CassandraService {

  implicit val ex = ExecutionContext.Implicits.global
  implicit val session = LocalIncomeDatabase.session
  implicit val keySpace = KeySpace(LocalIncomeDatabase.keyspace.name)

  def createIncomeTables = LocalIncomeDatabase.create()

  def addIncome(income:Income) = {
      LocalIncomeDatabase.Incomes.store(income)
  }
}
