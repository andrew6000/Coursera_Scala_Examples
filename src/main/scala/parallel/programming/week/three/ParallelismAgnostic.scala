package parallel.programming.week.three

import scala.collection.GenSeq

object ParallelismAgnostic {

  def largestPalindrome(xs: GenSeq[Int]): Int = {
    xs.aggregate(0)(
      (largest, n) => if (n > largest && n.toString == n.toString.reverse) n else largest,
      math.max
    )
  }

}
