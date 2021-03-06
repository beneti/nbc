package br.com.beneti.nubank

import org.scalatest.FunSpec
import scala.io.Source

/**
 * @author beneti
 */
class GraphSpec extends FunSpec {
  describe("Add an Edge") {
    
    def fixture =
      new {
        var graph = Graph()
        graph = graph.addEdge(new Edge(0, 1))
      }
    
    it("should create two vertices") {
      val graph = fixture.graph

      assert(graph.vertices.toSeq(0).id == 0)
      assert(graph.vertices.toSeq(1).id == 1)
    }

    it("should create a edge") {
      val graph = fixture.graph

      assert(graph.edges.toSeq(0).x == 0)
      assert(graph.edges.toSeq(0).y == 1)
    }
  }

  describe("Fraudulent") {
    
    def fixture =
      new {
        var graph = Graph()
        graph = graph.addEdge(Edge(0, 1))
        graph = graph.addEdge(Edge(0, 2))
        graph = graph.setFraudulent(0)
      }
    
    it("should set the informed vertex score to zero") {
      val graph = fixture.graph

      assert(graph.ranking(0).score == 0)
    }

    it("should halve all the neighbors vertices score") {
      val graph = fixture.graph
      
      assert(graph.ranking(1).score == 0.16666666666666666)
      assert(graph.ranking(2).score == 0.16666666666666666)
    }
  }

  describe("Centrality") {
    
    def fixture =
      new {
        var graph = Graph()
        val edges = Set((0, 2), (2, 3), (3, 1), (1, 0), (1, 2))
        edges.foreach((edge: (Int, Int)) => graph = graph.addEdge(Edge(edge._1, edge._2)))
      }

    it("should return the vertices ranked by score") {
      val graph = fixture.graph

      val vertices: Seq[Vertex] = graph.ranking
      assert(vertices(0).score == 0.25)
      assert(vertices(3).score == 0.3333333333333333)
    }

    it("should change ranking after set one vertex as fraudulent") {
      var graph = fixture.graph
      graph = graph.setFraudulent(0)

      val vertices: Seq[Vertex] = graph.ranking
      assert(vertices(0).score == 0)
      assert(vertices(3).score == 0.1875)
    }

  }

  describe("Centrality by given edges file") {

    def extractEdges(line: String): (Int, Int) = {
      val values = line.split("\\s+")
      (values(0).toInt, values(1).toInt)
    }

    var graph = Graph()
    val edges = Source.fromFile("src/test/scala/br/com/beneti/nubank/edges").getLines().map { case (line) => extractEdges(line) }
    edges.foreach((edge: (Int, Int)) => graph = graph.addEdge(Edge(edge._1, edge._2)))

    it("should return the vertices ranked by score") {
      var vertices: Seq[Vertex] = graph.ranking

      vertices = vertices.filter { x => x.id == 44 || x.id == 88 }

      assert("%.5f".format(vertices(0).score).toDouble == 0.00592)
      assert("%.5f".format(vertices(1).score).toDouble == 0.00599)
    }
  }

}