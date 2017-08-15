package db.phantom.database

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._
import db.phantom.connector.Connector
import db.phantom.model.IncomeModel

class IncomeDatabase(val keyspace: CassandraConnection) extends Database[IncomeDatabase](keyspace) {
  object Incomes extends IncomeModel with Connector
}

object Database extends IncomeDatabase(Connector.connector)

