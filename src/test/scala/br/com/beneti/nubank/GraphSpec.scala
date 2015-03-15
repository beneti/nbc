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
  
  describe("Fraudulent") {
    it("should decrease score for 0") {
      var graph = new Graph()
      graph = graph.addEdge(new Edge(0, 1))
      graph = graph.setFraudulent(0)

      assert(graph.vertices.toSeq(0).score == 0)
    }

    it("should decrease score for 0.5") {
      var graph = new Graph()
      graph = graph.addEdge(new Edge(0, 1))
      graph = graph.setFraudulent(0)

      assert(graph.vertices.toSeq(1).score == 0.5)
    }
  }

  describe("Centrality") {
    def fixture =
      new {
        var graph = new Graph
        val edges = Set((0, 2), (2, 3), (3, 1), (1, 0), (1, 2))
        edges.foreach((edge: (Int, Int)) => graph = graph.addEdge(new Edge(edge._1, edge._2)))
      }

    it("should return ranking") {
      val graph = fixture.graph;

      val vertices: Seq[Vertex] = graph.ranking
      assert(vertices(0).score == 0.25)
      assert(vertices(3).score == 0.3333333333333333)
    }
    
    it("should change ranking after set one vertex as fraudulent") {
      var graph = fixture.graph;
      graph = graph.setFraudulent(0)

      val vertices: Seq[Vertex] = graph.ranking
      assert(vertices(0).score == 0)
      assert(vertices(3).score == 0.1875)
    }

  }

}