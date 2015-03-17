package br.com.beneti.nubank

import org.json4s.DefaultFormats
import org.json4s.Formats
import org.scalatra._
import org.scalatra.json._

class Centrality extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal
  
  var graph = Graph()
  
  before() {
    contentType = formats("json")
  }
  
  get("/") {
    graph.ranking
  }
  
  post("/edge") {
    val newEdge = Edge(params("x").toInt, params("y").toInt)
    graph = graph.addEdge(newEdge)
    Created()
  }

  put("/vertex") {
    graph = graph.setFraudulent(params("id").toInt)
    Ok()
  }
}
