package parallel.programming.week.three

import org.scalameter.{Key, Warmer,config}

object SetIntersectionBenchmarkTest {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 100,
    Key.exec.benchRuns -> 100,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)


  def main(args: Array[String]): Unit = {

    val length = 2000000

    val set1 = (0 until length).toSet
    val set2 = (0 until length by 4).toSet
    val set1Par = (0 until length).par.toSet
    val set2Par = (0 until length by 4).par.toSet

    val seqtime1 = standardConfig measure {

      SetIntersection.intersection1(set1,set2)
    }

    val seqtime2 = standardConfig measure {

      SetIntersection.intersection2(set1,set2)
    }

    val partime1 = standardConfig measure {

      SetIntersection.intersection1(set1Par,set2Par)
    }

    val partime2 = standardConfig measure {

      SetIntersection.intersection2(set1Par,set2Par)
    }

    println(s"seqtime1 : $seqtime1 ms")
    println(s"seqtime2 : $seqtime2 ms")
    println(s"partime1 : $partime1 ms")
    println(s"partime2 : $partime2 ms")
  }
}
