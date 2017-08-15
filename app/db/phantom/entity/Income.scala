package db.phantom.entity

import java.util.UUID

/**
  *
  * This is the Scala representation of Songs, following the Datastax example
  */
case class Income (
                    id: UUID,
                    date:String,
                    time:String,
                    open:Double,
                    high:Double,
                    low:Double,
                    close:Double
                    //indicators:List[Double]
                  )