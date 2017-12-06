package parallel.programming.week.three

import org.scalameter.{Key, Warmer, config}

import scala.collection.parallel.mutable.ParArray

object ParallelismAgnosticBenchmarkTest {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 100,
    Key.exec.benchRuns -> 100,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def initializeArrayPar(xs: ParArray[Int]) {
    var i = 0
    while (i < xs.length) {
      xs(i) = i
      i += 1
    }
  }

  def initializeArray(xs: Array[Int]) {
    var i = 0
    while (i < xs.length) {
      xs(i) = i
      i += 1
    }
  }

  def main(args: Array[String]): Unit = {

    val length = 2000000
    val xs = new Array[Int](length)
    val xsPar = xs.par

    val seqtime = standardConfig setUp {
      _ => initializeArray(xs)
    }measure {

      ParallelismAgnostic.largestPalindrome(xs)
    }

    val partime = standardConfig  setUp {
      _ => initializeArrayPar(xsPar)
    }measure {
      ParallelismAgnostic.largestPalindrome(xsPar)
    }

    println(s"parallel time: $partime ms")
    println(s"speedup: ${seqtime / partime}")
  }

}
