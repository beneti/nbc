package br.com.beneti.nubank

import org.json4s.DefaultFormats
import org.json4s.Formats
import org.json4s.jackson.JsonMethods._
import org.scalatest.FunSpec
import org.scalatra.test.scalatest.ScalatraSuite
import br.com.beneti.nubank.api.Centrality

class CentralitySpec extends FunSpec with ScalatraSuite {
  addServlet(classOf[Centrality], "/api/centrality/*")
  protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal
  
  describe("POST /api/centrality/edge") {
    it("should return 201") {
      post("/api/centrality/edge", Array(("x", "0"), ("y", "1"))) {
        status should equal(201)
      }
    }
    
    it("should return 400") {
      post("/api/centrality/edge", Array(("x", "a"), ("lol", "1"))) {
        status should equal(400)
      }
    }
  }
  
  describe("GET /api/centrality") {
    it("should return a list of vertices ordered by score") {
      post("/api/centrality/edges", Array(("x", "0"), ("y", "1"))) {
        get("/api/centrality") {
          val ranking = parse(body).extract[List[Vertex]]
          ranking.size should equal(2)
          ranking(0).score should equal(1.0)
        }
      }
    }    
  }
  
  describe("PUT /api/centrality/vertex") {
    it("should return 200") {
      post("/api/centrality/edge", Array(("x", "0"), ("y", "1"))) {
        put("/api/centrality/vertex", Array(("id", "0"))) {
          status should equal(200)
        }
      }
    }    
    
    it("should return 400") {
      put("/api/centrality/vertex", Array(("id", "x"))) {
        status should equal(400)
      }
    }
  }
  
}
