package com.nevilon.firefly.core

import com.mongodb._
import gridfs.{GridFSDBFile, GridFS}
import java.io.{InputStream, File}
import org.bson.types.ObjectId

/**
 * Created with IntelliJ IDEA.
 * User: hudvin
 * Date: 1/1/13
 * Time: 8:54 AM
 * To change this template use File | Settings | File Templates.
 */
class MongoWrapper {

  private var mongoDb: Mongo = null
  private var db: DB = null
  private var coll: DBCollection = null
  private var fsStorage: GridFS = null


  def connect() {
    mongoDb = new Mongo("127.0.0.1", 27017)
    db = mongoDb.getDB("papers")
    coll = db.getCollection("files")
    fsStorage = new GridFS(db)
  }

  def cleanMongo(){
    coll.remove(new BasicDBObject())
    fsStorage.remove(new BasicDBObject())
  }

  def findFile(objectId:ObjectId):GridFSDBFile={
    fsStorage.findOne(objectId)
  }

  def updateFileDoc(fileDoc:DBObject){
    coll.save(fileDoc)
  }

  def listFileDocs():DBCursor={
    coll.find()
  }

  def uploadFileToMongo(file: File) {
    val fsFile = fsStorage.createFile(file)
    fsFile.save()

    val doc = new BasicDBObject()
    doc.put("md5", fsFile.getMD5)
    doc.put("file_oid", fsFile.getId)
    doc.put("local_path", file.getAbsolutePath)
    doc.put("local_name", fsFile.getFilename)
    doc.put("upload_date", fsFile.getUploadDate)
    doc.put("file_size", fsFile.getLength)
    coll.insert(doc)

    println(doc)
    //val imageForOutput = fsStorage.findOne(newFileName_)
  }

  def getFileStream(esId:String):InputStream={
    val fileDoc = coll.findOne(new BasicDBObject("es_id", esId))
    val file = fsStorage.findOne(new ObjectId(fileDoc.get("file_oid").toString  ))
    println(fileDoc)
    println(file)
    file.getInputStream


    /*
    val imageForOutput = fsStorage.findOne(newFileName)
    imageForOutput.writeTo("c:\\JavaWebHostingNew.pn")
    println(imageForOutput)
    */

  }

}
