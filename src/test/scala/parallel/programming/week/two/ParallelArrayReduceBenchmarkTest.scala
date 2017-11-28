package parallel.programming.week.two

import org.scalameter.{Key, Warmer, config}

object ParallelArrayReduceBenchmarkTest {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 30,
    Key.exec.maxWarmupRuns -> 100,
    Key.exec.benchRuns -> 1000,
    Key.verbose -> true
  )

  def initializeArray(xs: Array[Int]) {
    var i = 0
    while (i < xs.length) {
      xs(i) = i % 100
      i += 1
    }
  }

  def main(args: Array[String]): Unit = {

    def fPlus = (x: Int, y: Int) => x + y
    val length = 70000
    val xs = new Array[Int](length)

    val partime = standardConfig setUp {
      _ => val list = initializeArray(xs)
    } measure {

      ParallelArrayReduce.reduce(xs, fPlus)
    }

    println(s"parallel time: $partime ms")
  }

}
