package parallel.programming.week.two

import scala.annotation.tailrec

object ParallelArrayReduce {

  val threshold = 10000

  def reduceSeg[A](a: Array[A], left: Int, right: Int,
                                  f: (A, A) => A): A = {

    if(right - left < threshold) {
      var res = a(left)
      var i = left + 1/**/
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

  def reduceSeg2[A](a: Array[A], left: Int, right: Int,
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
      /*val (a1, a2) = parallel(reduceSeg(a, left, mid, f),
        reduceSeg(a, mid, right, f))*/
      val a1 = task{reduceSeg2(a, left, mid, f)}
      val a2 =reduceSeg2(a, mid, right, f)
      a1.join()

      f(a1.get(), a2)
    }
  }

  def reduceSeg3[A](arr: Array[A], left: Int, right: Int,a:A,
                    f: (A, A) => A): A = {

    if(right - left < threshold) {
      var res = arr(left)
      var i = left + 1
      while(i < right) {
        res = f(res, arr(i))
        i += 1
      }
      res
    } else {
      val mid = left + (right - left) / 2
      /*val (a1, a2) = parallel(reduceSeg(a, left, mid, f),
        reduceSeg(a, mid, right, f))*/
      val a1 = task{reduceSeg2(arr, left, mid, f)}
      val a2 =reduceSeg2(arr, mid, right, f)
      a1.join()

      f(a1.get(), a2)
    }
  }

  def reduce[A](a: Array[A], f: (A, A) => A): A = reduceSeg2(a, 0, a.length, f)

}
