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
                    date:String,
                    time:String,
                    open:Double,
                    high:Double,
                    low:Double,
                    close:Double,
                    i0:Double,
                    i1:Double,
                    i2:Double,
                    i3:Double,
                    i4:Double,
                    i5:Double,
                    i6:Double,
                    i7:Double,
                    i8:Double,
                    i9:Double,
                    i10:Double,
                    i11:Double,
                    i12:Double,
                    i13:Double,
                    i14:Double,
                    i15:Double,
                    i16:Double,
                    i17:Double,
                    i18:Double,
                    i19:Double,
                    i20:Double,
                    levelUp:Double,
                    correctionLevelUp:Double,
                    levelDown:Double,
                    correctionLevelDown:Double,
                    prediction:Integer,
                    label:Integer,
                    created_at: DateTime
                  )

/**
  * Object Income
  */
object Income {
  implicit val formatter = Json.format[Income]
}