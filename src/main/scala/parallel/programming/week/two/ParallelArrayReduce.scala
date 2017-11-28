package parallel.programming.week.two

object ParallelArrayReduce {

  val threshold = 10000

  def reduceSeg[A](a: Array[A], left: Int, right: Int,
                                  f: (A, A) => A): A = {

    if(right - left < threshold) {
      var res = a(left)
      var i = left + 1
      while(i < right) {
        res = f(res, a(i))
        i += 1
      }
      res
    } else {
      val mid = left + (right - left) / 2
      val (a1, a2) = parallel(reduceSeg(a, left, mid, f),
                          reduceSeg(a, mid, right, f))
      f(a1, a2)
    }
  }

  def reduce[A](a: Array[A], f: (A, A) => A): A = reduceSeg(a, 0, a.length, f)

}
