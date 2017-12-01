package parallel.programming.week.two

import org.scalameter._

object MergeSortBenchmarkTest {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 60,
    Key.exec.benchRuns -> 60,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def initialize(xs: Array[Int]) {
    var i = 0
    while (i < xs.length) {
      xs(i) = i % 100
      i += 1
    }
  }

  def main(args: Array[String]): Unit = {

    val length = 10000000
    val maxDepth = 7
    val xs = new Array[Int](length)

    val seqtime = standardConfig setUp {
      _ => initialize(xs)
    } measure {
      MergeSort.seqMergeSort(xs)
    }

    val partime = standardConfig setUp {
      _ => initialize(xs)
    } measure {
      MergeSort.parMergeSort(xs, maxDepth)
    }

    val partimeWithTask = standardConfig setUp {
      _ => initialize(xs)
    } measure {
      MergeSortWithTask.parMergeSortWithTask(xs, maxDepth)
    }
    println(s"parallel time: $partime ms")
    println(s"speedup: ${seqtime / partime}")

    println(s"\nparallel time with task: $partimeWithTask ms")
    println(s"speedup task vs. no task: ${partime / partimeWithTask}")
  }

}
