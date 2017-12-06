package parallel.programming.week.three
import org.scalameter.{Key, Warmer, config}

import scala.collection.immutable.TreeMap

//http://docs.scala-lang.org/overviews/parallel-collections/conversions.html
object ConverstionBenchmarkTest {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 10,
    Key.exec.maxWarmupRuns -> 20,
    Key.exec.benchRuns -> 20,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  val length = 10000000

  val array = (Array.fill(length)(""))
  val list = array.toList
  val set = array.toSet
  val vector = array.toVector
  val range = Range(0,length)
  val map = array.zip(array.indices).toMap

  def roundBy3(n:Double)= BigDecimal(n).setScale(3, BigDecimal.RoundingMode.HALF_UP).toDouble



  def main(args: Array[String]) {


    val listtime = standardConfig measure {
      list.par
    }

    val listMap:Map[String,Double] = TreeMap("list" -> roundBy3(listtime))

    val arraytime = standardConfig measure {
      array.par
    }

    val arrayMap:Map[String,Double] = TreeMap("array" -> roundBy3(arraytime))++listMap

    val settime = standardConfig measure {
      set.par
    }

    val setMap:Map[String,Double] = TreeMap("set" -> roundBy3(settime))++arrayMap

    val vectortime = standardConfig measure {
      vector.par
    }

    val vectorMap:Map[String,Double] = TreeMap("vector" -> roundBy3(vectortime))++setMap

    val rangetime = standardConfig measure {
      range.par
    }

    val rangeMap = TreeMap("range" -> roundBy3(rangetime))++vectorMap

    val maptime = standardConfig measure {
      map.par
    }

    val tmap = (TreeMap("map" -> roundBy3(maptime))++rangeMap).toList.sortBy (_._2)

    for ((k,v) <- tmap) println(s"$k, time: $v")

    /*
    range, time: 0.001
    vector, time: 0.001
    map, time: 0.085
    set, time: 0.112
    array, time: 73.106
    list, time: 284.354
    */
  }

}
