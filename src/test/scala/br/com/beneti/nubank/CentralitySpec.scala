package br.com.beneti.nubank

import org.json4s.DefaultFormats
import org.json4s.Formats
import org.json4s.jackson.JsonMethods._
import org.scalatest.FunSpec
import org.scalatra.test.scalatest.ScalatraSuite
import br.com.beneti.nubank.Centrality

class CentralitySpec extends FunSpec with ScalatraSuite {
  addServlet(classOf[Centrality], "/api/centrality/*")
  protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal
  
  describe("POST /api/centrality/edge") {
    it("should return 201") {
      post("/api/centrality/edge", Array(("x", "0"), ("y", "1"))) {
        status should equal(201)
      }
    }    
  }
  
  describe("GET /api/centrality") {
    it("should return list of vertices with and order by scores") {
      post("/api/centrality/edges", Array(("x", "0"), ("y", "1"))) {
        get("/api/centrality") {
          val ranking = parse(body).extract[List[Vertex]]
          ranking.size should equal(2)
          ranking(0).score should equal(1.0)
        }
      }
    }    
  }
  
}
