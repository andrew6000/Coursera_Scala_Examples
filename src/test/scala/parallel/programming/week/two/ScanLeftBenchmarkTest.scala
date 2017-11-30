package parallel.programming.week.two

import org.scalameter._

object ScanLeftBenchmarkTest {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 30,
    Key.exec.maxWarmupRuns -> 100,
    Key.exec.benchRuns -> 100,
    Key.verbose -> true
  )

  def initializeArray(xs: Array[Int]) {
    var i = 0
    while (i < xs.length) {
      xs(i) = i
      i += 1
    }
  }

  def main(args: Array[String]): Unit = {

    def fPlus = (x: Int, y: Int) => x + y
    val length = 7000
    val xs = new Array[Int](length)

    val seqtime = standardConfig setUp {
      _ => val list = initializeArray(xs)
    } measure {

      val out = new Array[Int](length+1)
      SequentialScanLeft.scanLeftSeq(xs,0,fPlus,out)
    }

   /* val partime = standardConfig setUp {
      _ => val list = initializeArray(xs)
    } measure {

      val out = new Array[Int](length+1)
      ParallelScanLeft.scanLeftViaMapReduce(xs,0,fPlus,out)
    }*/

    println(s"sequential time: $seqtime ms")
 /*   println(s"parallel time: $partime ms")
    println(s"speedup time: ${ seqtime/partime}")*/
  }

}
