package com.nevilon.firefly.core

import java.io._
import org.bson.types.ObjectId

/**
 * Created with IntelliJ IDEA.
 * User: hudvin
 * Date: 12/28/12
 * Time: 7:19 AM
 * To change this template use File | Settings | File Templates.
 */
object Importer {

  private var mongoWrapper:MongoWrapper = null

  def main(args: Array[String]) {
    /*
    mongoWrapper = new MongoWrapper()
    mongoWrapper.connect()
    mongoWrapper.cleanMongo()
    */
    val result = ESWrapper.sendSearchQuery("amplifier")
    println(result)
    //ESWrapper.search()

    //uploadAllToMongo()
   // sendToES()
  }

  def uploadAllToMongo(){
    val files = FSUtils.tree(new File("/home/hudvin/")).filter(f => f.isFile && f.getName.endsWith(".pdf"))
    files.foreach(mongoWrapper.uploadFileToMongo)
  }

  def sendToES(){
    import scala.collection.JavaConversions._
    mongoWrapper.listFileDocs().iterator().foreach(dbFile=>{
      val fileOid = dbFile.get("file_oid").toString
      val fsFile = mongoWrapper.findFile(new ObjectId(fileOid))
      ESWrapper.sendToES(fsFile.getInputStream)
    })
  }






}
