package parallel.programming.week.one

import org.scalameter.{Key, Warmer, config}

object PNormCalcTest {

  import util.Random.nextInt

  val numbers = Stream.continually(nextInt(100))
  val num = 1000000
  lazy val arr = numbers.take(num).toArray

  def main(args: Array[String]): Unit = {

    val standardConfig = config(
      Key.exec.minWarmupRuns -> 5,
      Key.exec.maxWarmupRuns -> 10,
      Key.exec.benchRuns -> 100,
      Key.verbose -> true
    ) withWarmer (new Warmer.Default)

    val timePNormSeq = standardConfig measure {

      SumPowersOfArraySegment.pNorm(arr, 2)
    }

    val timePNormTwoPartSeq = standardConfig measure {

      SumPowersOfArraySegment.pNormTwoPart(arr, 2)
    }

    val timePNormTwoPartParallel = standardConfig measure {

      SumPowersOfArraySegment.pNormTwoPartParallel(arr,2)
    }

    val timePNormFourPartParallel = standardConfig measure {

      SumPowersOfArraySegment.pNormFourPartParallel(arr,2)
    }

    val timePNormRec = standardConfig measure {

      SumPowersOfArraySegment.pNormRec(arr,2)
    }

    println(s"\n\nSequential P Norm time: $timePNormSeq ms")
    println(s"Sequential two-part P Norm time: $timePNormTwoPartSeq ms")
    println(s"speedup: ${timePNormSeq / timePNormTwoPartSeq}\n")
    println(s"Parallel two-part P Norm time: $timePNormTwoPartParallel ms")
    println(s"speedup: ${timePNormTwoPartSeq / timePNormTwoPartParallel}\n")
    println(s"Parallel four-part P Norm time: $timePNormFourPartParallel ms")
    println(s"speedup: ${timePNormTwoPartParallel / timePNormFourPartParallel}\n")
    println(s"Parallel recursive P Norm time: $timePNormRec ms")
    println(s"speedup: ${timePNormFourPartParallel / timePNormRec}")
  }

}
