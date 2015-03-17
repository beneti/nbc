package br.com.beneti.nubank

import scala.math.pow

/**
 * @author beneti
 */
case class Graph(vertices: Set[Vertex] = Set.empty[Vertex],
                 edges: Set[Edge] = Set.empty[Edge]) {

  private var verticesIndexes: Array[Int] = Array.empty[Int]
  private var matrix = Array.fill(vertices.size, vertices.size)(Int.MaxValue / 2)
  private var edgesAsSeq = edges.toSeq
  private var verticesAsSeq = vertices.toSeq

  vertices.toSeq.foreach ( x => verticesIndexes :+= x.id )

  def addEdge(edge: Edge) = {
    var newVertices = vertices

    if (verticesIndexes.indexOf(edge.x) == -1)
      newVertices += Vertex(edge.x)

    if (verticesIndexes.indexOf(edge.y) == -1)
      newVertices += Vertex(edge.y)

    Graph(newVertices, edges + edge)
  }

  def ranking =
    this.perform.vertices.toSeq.sortBy(v => v.score)

  def setFraudulent(id: Int) = {
    var newVertices: Set[Vertex] = Set.empty[Vertex]
    var index = verticesIndexes.indexOf(id)
    for (i <- 0 until matrix.length) {
      val currentVertex = verticesAsSeq(i)
      newVertices += Vertex(currentVertex.id, currentVertex.score, if (currentVertex.id == id) 0 else currentVertex.fraudScore)
    }
    
    Graph(newVertices, edges)
  }

  private def perform = {

    for (i <- 0 until edges.size) {

      var x = verticesIndexes.indexOf(edgesAsSeq(i).x)
      var y = verticesIndexes.indexOf(edgesAsSeq(i).y)

      matrix(x)(y) = 1
      matrix(y)(x) = 1
      matrix(x)(x) = 0
      matrix(y)(y) = 0
    }

    for {
      k <- 0 until matrix.length
      i <- 0 until matrix.length
      j <- 0 until matrix.length
    } {
      var distance = matrix(i)(k) + matrix(k)(j)
      if (distance < matrix(i)(j)) {
        matrix(i)(j) = distance
      }
    }

    var newVertices: Set[Vertex] = Set.empty[Vertex]

    for (i <- 0 until verticesAsSeq.length) {
      var sum = 0
      var fraudScore = 1.0
      for (j <- 0 until verticesAsSeq.length) {
        val distance = matrix(i)(j)
        if (distance < Int.MaxValue / 2)
           sum += distance
        if (verticesAsSeq(j).fraudScore == 0)
          fraudScore = fraudScore * (1.0 - pow(1.0/2, distance))
      }
      val id = verticesAsSeq(i).id
      val score = 1.0 / sum * fraudScore
      newVertices += Vertex(id, score, fraudScore)
    }

    Graph(newVertices, edges)

  }

}