package parallel.programming.week.two

object ParallelPointwiseExponent {

  val threshold = 10000

  def normsOfPar(inp: Array[Int], p: Double,
                 left: Int, right: Int,
                 out: Array[Double]): Unit = {
    if (right - left < threshold) {
      var i= left
      while (i < right) {
        out(i)= power(inp(i),p)
        i= i+1
      }
    } else {
      val mid = left + (right - left)/2
      val _ = parallel(normsOfPar(inp, p, left, mid, out),
        normsOfPar(inp, p, mid, right, out))
    }
  }

}
