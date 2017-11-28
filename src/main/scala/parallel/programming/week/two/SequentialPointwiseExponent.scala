package parallel.programming.week.two

object SequentialPointwiseExponent {

  def normsOf(inp: Array[Int], p: Double,
              left: Int, right: Int,
              out: Array[Double]): Unit = {
    var i= left
    while (i < right) {
      out(i)= power(inp(i),p)
      i= i+1
    }
  }

}
