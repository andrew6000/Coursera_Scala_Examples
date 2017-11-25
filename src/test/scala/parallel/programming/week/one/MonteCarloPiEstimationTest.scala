package parallel.programming.week.one

import org.scalameter._

object MonteCarloPiEstimationTest {

  @volatile var seqResult: Double = 0
  @volatile var parResult: Double = 0

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 100,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {

    val iterations = 400000000
    val seqtime = standardConfig measure {
      seqResult = MonteCarloPiEstimation.monteCarloPiSeq(iterations)
    }
    println(s"sequential time: $seqtime ms")

    val partime = standardConfig measure {
      parResult = MonteCarloPiEstimation.parMonteCarloPi(iterations)
    }

    println(s"fork/join time: $partime ms")
    println(s"speedup: ${seqtime/partime}")
    println(s"values computed are $seqResult vs $parResult")
  }

}
