package parallel.programming.week.three

import scala.collection._

object LargestPalindrome {

  /** Writing Parallelism-Agnostic Code
    * Generic collection trait allow us to write code that is
    * unaware of parallelism
    */
  def largestPalindrome(xs: GenSeq[Int]): Int = {
    xs.aggregate(Int.MinValue)((largest, n) =>
      if(n > largest && n.toString == n.toString.reverse) n else largest,
      math.max
    )
  }
}
