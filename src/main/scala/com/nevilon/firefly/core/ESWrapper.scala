package com.nevilon.firefly.core

import java.io.InputStream
import org.apache.commons.io.IOUtils
import org.apache.commons.codec.binary.Base64
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.{StringRequestEntity, PostMethod}
import java.util
import com.codahale.jerkson.Json
import scala.Predef._

/**
 * Created with IntelliJ IDEA.
 * User: hudvin
 * Date: 1/1/13
 * Time: 9:11 AM
 * To change this template use File | Settings | File Templates.
 */
object ESWrapper {

  def sendToES(is: InputStream) {
    val bytes = IOUtils.toByteArray(is)
    val encoded = Base64.encodeBase64(bytes)

    val httpClient = new HttpClient()
    val post = new PostMethod("http://localhost:9200/test/attachment/")
    val base64Str = new String(encoded)
    val json = "{\"file\":\"" + base64Str + "\"}"

    val requestEntity = new StringRequestEntity(json, "application/json", "UTF-8")
    post.setRequestEntity(requestEntity)
    val response = httpClient.executeMethod(post)
  }

  def sendSearchQuery(term:String):QueryResult = {
    val jsonQuery = buildSearchQuery(term)
    val httpClient = new HttpClient()
    val post = new PostMethod("http://localhost:9200/_search?pretty=true")
    val requestEntity = new StringRequestEntity(jsonQuery, "application/json", "UTF-8")
    post.setRequestEntity(requestEntity)
    val response = httpClient.executeMethod(post)
    val responseBody = post.getResponseBodyAsString
    println(post.getResponseBodyAsString)
    val queryResult = parseSearchResponse(responseBody)
    return queryResult
  }

  def buildSearchQuery(term: String):String = {
    val data = Map("fields" -> Array("title"),
      "query" -> Map("query_string" -> Map("query" -> term)),
      "highlight" -> Map("fields"->Map("file"->Map())))
    val res = Json.generate(data)
    return res
  }

  def parseSearchResponse(jsonResponse:String): QueryResult = {
    val json: util.LinkedHashMap[String, String] = Json.parse(jsonResponse)

    import scala.collection.JavaConversions._
    val queryResult = new QueryResult()

    val took: Int = json.get("took").asInstanceOf[Int]
    val timedOut: Boolean = json.get("timed_out").asInstanceOf[Boolean]
    //shards
    val _shards: util.LinkedHashMap[String, Int] = json.get("_shards").asInstanceOf[util.LinkedHashMap[String, Int]]
    val total: Int = _shards.get("total")
    val succesful: Int = _shards.get("succesful")
    val failed = _shards.get("failed")
    //hits meta
    val hits = json.get("hits").asInstanceOf[util.LinkedHashMap[String, String]]
    val hitsTotal = hits.get("total").asInstanceOf[Int]
    val hitsMaxScore: Double = hits.get("max_score").asInstanceOf[Double]

    //fill
    queryResult.took = took
    queryResult.timedOut = timedOut
    queryResult.shardsTotal = total
    queryResult.shardsSuccessful = succesful
    queryResult.shardsFailed = failed

    queryResult.hitsTotal = hitsTotal
    queryResult.hitsMaxScore = hitsMaxScore


    val hitsList = hits.get("hits").asInstanceOf[util.ArrayList[Any]]

    hitsList.foreach(e => {
      val hitResult = new HitResult()
      val items = e.asInstanceOf[util.LinkedHashMap[String, String]]
      items.foreach(kv => {

        kv._1 match {
          case "_index" => {
            hitResult.index = kv._2
          }
          case "_type" => {
            hitResult._type = kv._2
          }
          case "_id" => {
            hitResult.id = kv._2
          }
          case "_score" => {
            hitResult.score = kv._2.asInstanceOf[Double]
          }
          case "highlight" => {
            kv._2.asInstanceOf[util.LinkedHashMap[String, String]].foreach(field => {
              hitResult.highlight.put(field._1, field._2)
            })
          }
          case "fields" => {
            val fields = kv._2.asInstanceOf[util.LinkedHashMap[String, String]]
            fields.foreach(field => {
              hitResult.fields.put(field._1, field._2)
            })
          }
        }
      }
      )
      queryResult.hits += hitResult
    }

    )


    return  queryResult
  }

}
