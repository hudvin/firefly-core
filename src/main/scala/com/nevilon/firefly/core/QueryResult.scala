package com.nevilon.firefly.core

import collection.mutable
import collection.mutable.ListBuffer

/**
 * Created with IntelliJ IDEA.
 * User: hudvin
 * Date: 1/2/13
 * Time: 3:56 AM
 * To change this template use File | Settings | File Templates.
 */
class QueryResult {

  var took:Int = 0
  var timedOut:Boolean = false
  var shardsTotal:Int  = 0
  var shardsSuccessful:Int = 0
  var shardsFailed:Int = 0

  var hitsTotal:Int = 0
  var hitsMaxScore:Double = 0.0d
  var hits = ListBuffer[HitResult]()

}

class HitResult{

  var index:String = ""
  var _type: String = ""
  var id:String  = ""
  var score:Double = 0.0d
  var fields = mutable.HashMap[String,String]()
  var highlight = mutable.HashMap[String,String]()


}
