package functional.programming.week.one

import org.scalameter.{Key, Warmer, config}

object CountingChangeBenchmarkTest {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 100,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {

    val timeCountChange1 = standardConfig measure {

      CountChange.countChange1(1000)
    }

    println(s"\ncountChange1 time: $timeCountChange1 ms")
  }


}
