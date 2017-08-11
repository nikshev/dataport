package models

import play.api.libs.json.Json

/**
  * Income information class
 *
  * @param date - tick date
  * @param time - time date
  * @param open - open price
  * @param high - high price
  * @param low  - low  price
  * @param close - close price
  * @param indicators - indicators list
  */
case class Income (
                   date:String,
                   time:String,
                   open:Double,
                   high:Double,
                   low:Double,
                   close:Double,
                   indicators:List[Double]
                  )
/**
  * Object Income
  */
object Income {
  implicit val formatter = Json.format[Income]
}