package parallel.programming.week.three

import org.scalameter.{Key, Warmer, config}

object FoldBenchmarkTest {

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

    val foldLeftSeqSumTime = standardConfig setUp {
      _ => initializeArray(xs)
    }measure {

      Fold.sum(xs)
    }

    val foldLeftParSumTime = standardConfig setUp {
      _ => initializeArray(xs)
    }measure {

      Fold.parSum(xs)
    }

    println(s"sequential foldLeft sum over array time: $foldLeftSeqSumTime  ms")
    println(s"parallel foldLeft sum over array time: $foldLeftSeqSumTime  ms")
    println(s"speedup of parallel vs. sequentional array fold sum: ${foldLeftSeqSumTime / foldLeftParSumTime}")
  }



}
