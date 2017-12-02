package parallel.programming.week.two

import org.scalameter.{Key, Warmer, config}

object PointwiseExponentBenchmarkTest {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 100,
    Key.exec.benchRuns -> 100,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  val f = (x: Int) => x*x

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

    val timeNormsOf = standardConfig setUp {
      _ => initializeArray(xs)
    } measure {

      val out = new Array[Double](length)
      SequentialPointwiseExponent.normsOf(xs,2,0,xs.length,out)
    }

    val timeNormsOfPar = standardConfig setUp {
      _ => val list = initializeArray(xs)
    } measure {

      val out = new Array[Double](length)
      ParallelPointwiseExponent.normsOfPar(xs,2,0,xs.length,out)
    }

    println(s"sequential array time: $timeNormsOf  ms")
    println(s"parallel array time: $timeNormsOfPar  ms")
    println(s"speedup of parallel vs. sequentional: ${timeNormsOf / timeNormsOfPar}")


  }

}
