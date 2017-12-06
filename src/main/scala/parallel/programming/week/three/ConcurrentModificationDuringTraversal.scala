package parallel.programming.week.three

import scala.collection._

object ConcurrentModificationDuringTraversal {

  def wrong() {
    val graph = mutable.Map[Int, Int]() ++= (0 until 100000).map(i => (i, i + 1))
    graph(graph.size - 1) = 0
    for ((k, v) <- graph.par) graph(k) = graph(v)
    val violation = graph.find({ case (i, v) => v != (i + 2) % graph.size })
    println(s"violation: $violation")
  }

  def right(): Unit ={

    val graph = concurrent.TrieMap[Int, Int]() ++= (0 until 100000).map(i => (i, i + 1))
    graph(graph.size - 1) = 0
    val previous = graph.snapshot()
    for ((k, v) <- graph.par) graph(k) = previous(v)
    val violation = graph.find({ case (i, v) => v != (i + 2) % graph.size })
    println(s"violation: $violation")
  }

}
