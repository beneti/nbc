package br.com.beneti.nubank.api

import org.json4s.DefaultFormats
import org.json4s.Formats
import org.scalatra._
import org.scalatra.json._
import br.com.beneti.nubank.Graph
import br.com.beneti.nubank.Edge

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
    try {
      val newEdge = Edge(params("x").toInt, params("y").toInt)
      graph = graph.addEdge(newEdge)
      Created()      
    } catch {
      case e: Exception =>
        BadRequest()
    }
  }

  put("/vertex") {
    try {
      graph = graph.setFraudulent(params("id").toInt)
      Ok()
    } catch {
      case e: Exception =>
        BadRequest()
    }
  }
}
