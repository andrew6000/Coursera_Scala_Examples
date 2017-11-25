package parallel.programming.week.one

import scala.util.Random


object MonteCarloPiEstimation {

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
