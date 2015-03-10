package br.com.beneti.nubank

/**
 * @author beneti
 */
case class Graph(vertices: Set[Vertex] = Set.empty[Vertex], 
  edges: Set[Edge] = Set.empty[Edge]) {

  def addEdge(edge: Edge) = 
    new Graph(vertices + Vertex(edge.x) + Vertex(edge.y), edges + edge)

  def rankingByCloseness = {
    this.ranking.vertices.toSeq.sortBy(v => v.closeness)
  }

  def rankingByFarness = {
    this.ranking.vertices.toSeq.sortBy(v => v.farness)
  }

  private def ranking = {
    var matrixSize = vertices.size
    var matrix = Array.fill(matrixSize, matrixSize)(Int.MaxValue/2)
    var edgesAsSeq = edges.toSeq

    for (i <- 0 until edges.size) {
      var x = edgesAsSeq(i).x
      var y = edgesAsSeq(i).y
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

    var verticesWithScores: Set[Vertex] = Set.empty[Vertex]

    for (i <- 0 until matrix.length) {
      var sum = matrix(i).sum
      verticesWithScores += new Vertex(i, 1.0/sum, sum)
    }

    new Graph(verticesWithScores, edges)

  }

}