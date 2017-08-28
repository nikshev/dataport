package db.phantom.entity

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  *
  * This is the Scala representation of Songs, following the Datastax example
  */
case class Income (
                    id: UUID,
                    symbol:String,
                    timeframe:String,
                    date:String,
                    time:String,
                    open:Double,
                    high:Double,
                    low:Double,
                    close:Double,
                    rawFeatures:List[Double],
                    levelUp:Double,
                    correctionLevelUp:Double,
                    levelDown:Double,
                    correctionLevelDown:Double,
                    prediction:Int,
                    label:Int,
                    createdAt: DateTime
                  )


/**
  * Object Income
  */
object Income {

  implicit val formatter = Json.format[Income]

}
