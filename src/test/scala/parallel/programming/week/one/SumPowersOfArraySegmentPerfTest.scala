package parallel.programming.week.one

import org.scalameter._

object SumPowersOfArraySegmentPerfTest  {

  import util.Random.nextInt
  val numbers = Stream.continually(nextInt(100))
  val num = 10000000
  lazy val arr = numbers.take(num).toArray

  def main(args: Array[String]): Unit = {

    val standardConfig = config(
      Key.exec.minWarmupRuns -> 5,
      Key.exec.maxWarmupRuns -> 10,
      Key.exec.benchRuns -> 50,
      Key.verbose -> true
    ) withWarmer (new Warmer.Default)

    val seqtime = standardConfig measure {

      SumPowersOfArraySegment.sumSegment(arr, 2, 0, arr.length)
    }

    println(s"sequential sum time: $seqtime ms")

    val partime = standardConfig measure {

      SumPowersOfArraySegment.segmentRec(arr, 2, 0, arr.length)
    }

    println(s"sequential sum time: $seqtime ms")
    println(s"parallel sum time: $partime ms")
    println(s"speedup: ${seqtime / partime}")

  }

}
