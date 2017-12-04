package parallel.programming.week.three

import org.scalameter.{Key, Warmer, config}

object LargestPalindromeBenchmarkTest {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 100,
    Key.exec.benchRuns -> 100,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def initializeArray(xs: Array[Int]) {
    var i = 0
    while (i < xs.length) {
      xs(i) = i % 100
      i += 1
    }
  }

  def main(args: Array[String]): Unit = {

    val length = 2000000
    val xs = new Array[Int](length)

    val seqtime = standardConfig setUp {
      _ => initializeArray(xs)
    }measure {

      LargestPalindrome.largestPalindrome(xs)
    }

    val partime = standardConfig setUp {
      _ => initializeArray(xs)
    }measure {

      LargestPalindrome.largestPalindrome(xs.par)
    }

    println(s"sequential foldLeft sum over array time: $seqtime  ms")
    println(s"parallel foldLeft sum over array time: $partime  ms")
    println(s"speedup of parallel vs. sequentional array fold sum: ${seqtime / partime}")
  }
}
