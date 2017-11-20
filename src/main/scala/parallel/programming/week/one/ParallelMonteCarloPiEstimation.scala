package parallel.programming.week.one


import org.scalameter._
import scala.util.Random


object ParallelMonteCarloPiEstimation {

  @volatile var seqResult: Double = 0
  @volatile var parResult: Double = 0

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 20,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {

    val iterations = 4000000
    val seqtime = standardConfig measure {
      seqResult = monteCarloPiSeq(iterations)
    }
    println(s"sequential time: $seqtime ms")

    val partime = standardConfig measure {
      parResult = parMonteCarloPi(iterations)
    }

    println(s"fork/join time: $partime ms")
    println(s"speedup: ${seqtime/partime}")
    println(s"values computed are $seqResult vs $parResult")
  }

  def mCount(iterations: Int): Double = {
    val randomX = new Random
    val randomY = new Random
    var hits = 0
    for (i <- 0 until iterations) {
      val x = randomX.nextDouble()
      val y = randomY.nextDouble()
      if (x * x + y * y < 1) hits += 1
    }
    hits
  }

  def monteCarloPiSeq(iter: Int): Double = 4.0 * mCount(iter)/iter

  def parMonteCarloPi(iterations: Int): Double = {
    val ((pi1, pi2), (pi3, pi4)) = parallel(
      parallel(mCount(iterations / 4), mCount(iterations / 4)),
      parallel(mCount(iterations / 4), mCount(iterations-3*(iterations/4)))
    )
    4.0* (pi1 + pi2 + pi3 + pi4) / iterations
  }



}
