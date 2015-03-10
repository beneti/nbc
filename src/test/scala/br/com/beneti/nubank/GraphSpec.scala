package br.com.beneti.nubank

import org.scalatest.FunSpec

/**
 * @author beneti
 */
class GraphSpec extends FunSpec {
  describe("Add an Edge") {
    it("should create two vertices") {
      var graph = new Graph()
      graph = graph.addEdge(new Edge(0, 1))
      
      assert(graph.vertices.toSeq(0).id == 0)
      assert(graph.vertices.toSeq(1).id == 1)
    }
    
    it("should create a edge") {
      var graph = new Graph()
      graph = graph.addEdge(new Edge(0, 1))
      
      assert(graph.edges.toSeq(0).x == 0)
      assert(graph.edges.toSeq(0).y == 1)
    }
  }
  
  describe("Centrality") {
    def fixture =
    new {
      var graph = new Graph
      val edges = Set((0,2), (2,3), (3,1), (1,0), (1,2))
      edges.foreach((edge: (Int, Int)) => graph = graph.addEdge(new Edge(edge._1,edge._2)))
    }
    
    it("should return vertices order by closeness") {
      val graph = fixture.graph;
      
      val vertices: Seq[Vertex] = graph.rankingByCloseness
      assert(vertices(0).id == 0)
      assert(vertices(3).id == 2)
    }
    
    it("should return vertices order by farness") {
      val graph = fixture.graph;
      
      val vertices: Seq[Vertex] = graph.rankingByFarness
      assert(vertices(0).id == 1)
      assert(vertices(3).id == 3)
    }
    
  }
  
}