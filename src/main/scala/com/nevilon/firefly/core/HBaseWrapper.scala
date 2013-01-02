package com.nevilon.firefly.core


/**
 * Created with IntelliJ IDEA.
 * User: hudvin
 * Date: 1/1/13
 * Time: 9:00 AM
 * To change this template use File | Settings | File Templates.
 */
class HBaseWrapper {
//
//  private val UUID_FAMILY = "uuid"
//  private val FILE_DATA_FAMILY = "file_data"
//  private val HASH_FAMILY = "hash"
//  private val SOURCE_FAMILY = "source"
//
//  private val HBASE_MASTER_KEY = "hbase.master"
//  private val HBASE_MASTER_VALUE = "localhost:60000"
//
//  private val DEFAULT_TABLE_NAME = "files"
//
//  private var conf: Configuration = null
//  private var hbase: HBaseAdmin = null
//  private var table: HTable = null


  //  private def connect() {
//    conf = HBaseConfiguration.create()
//    conf.set(HBASE_MASTER_KEY, HBASE_MASTER_VALUE)
//    conf.set("hbase.client.keyvalue.maxsize", "-1")
//    hbase = new HBaseAdmin(conf)
//    if (!hbase.tableExists(DEFAULT_TABLE_NAME)) {
//      val tableDesc: HTableDescriptor = new HTableDescriptor(DEFAULT_TABLE_NAME)
//      tableDesc.addFamily(new HColumnDescriptor(UUID_FAMILY))
//      tableDesc.addFamily(new HColumnDescriptor(FILE_DATA_FAMILY))
//      tableDesc.addFamily(new HColumnDescriptor(HASH_FAMILY))
//      tableDesc.addFamily(new HColumnDescriptor(SOURCE_FAMILY))
//      hbase.createTable(tableDesc)
//    }
//    table = new HTable(conf, DEFAULT_TABLE_NAME)
//  }

//  def loadFile(file: File) {
//    println(file)
//
//    val is = new FileInputStream(file)
//    val cnt = is.available
//    val bytes = Array.ofDim[Byte](cnt)
//    is.read(bytes)
//    is.close()
//
//    val p = new Put(Bytes.toBytes(System.currentTimeMillis()))
//    p.add(Bytes.toBytes(UUID_FAMILY), Bytes.toBytes(""), Bytes.toBytes("11111"))
//    p.add(Bytes.toBytes(FILE_DATA_FAMILY), Bytes.toBytes(""), Bytes.toBytes(ByteBuffer.wrap(bytes)))
//    p.add(Bytes.toBytes(HASH_FAMILY), Bytes.toBytes(""), Bytes.toBytes("hash"))
//    p.add(Bytes.toBytes(SOURCE_FAMILY), Bytes.toBytes(""), Bytes.toBytes("source"))
//    table.put(p)
//    table.flushCommits()
//  }
//
//  def scan() {
//    val scan = new Scan()
//    //scan.addColumn(Bytes.toBytes(UUID_FAMILY), Bytes.toBytes(""))
//    //scan.addFamily(Bytes.toBytes(UUID_FAMILY))
//    val scanner: ResultScanner = table.getScanner(scan)
//    scan.setCaching(5000)
//
//    //var counter = 0
//
//    import scala.collection.JavaConversions._
//    scanner.foreach(result => {
//      val str = new String((result.getValue(Bytes.toBytes(UUID_FAMILY), Bytes.toBytes(""))))
//      println(str)
//
//      val fileData = result.getValue(Bytes.toBytes(FILE_DATA_FAMILY), Bytes.toBytes(""))
//      println(fileData.length)
//
//
//      val out = new FileOutputStream("/tmp/filename.pdf")
//      out.write(fileData)
//      out.close()
//
//
//    })
//
//
//    //println(counter)
//    //
//    //    for (var rr = scanner.next(); rr != null; rr = scanner.next()) {
//    //      // print out the row we found and the columns we were looking for
//    //      System.out.println("Found row: " + rr)
//    //    }
//
//  }
}
