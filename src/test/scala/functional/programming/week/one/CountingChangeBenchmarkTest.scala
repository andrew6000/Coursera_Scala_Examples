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

    val timeCountChange2 = standardConfig measure {

      CountChange.countChange2(1000,List(1,5,10,25,50))
    }


    println(s"\ncountChange1 time: $timeCountChange1 ms")
    println(s"\ncountChange2 time: $timeCountChange2 ms")
    println(s"speedup  (countChange1 vs. countChange2): ${timeCountChange1 / timeCountChange2}\n")

  }


}
