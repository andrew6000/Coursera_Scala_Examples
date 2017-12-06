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

  val array:Array[String] = (Array.fill(length)(""))
  val list = array.toList
  val set:Set[String] = array.toSet
  val vector = array.toVector
  val range = Range(0,length)
  val map:scala.collection.immutable.Map[String,Int] = array.zip(array.indices).toMap
  val mutableSet:scala.collection.mutable.HashSet[String] = (scala.collection.mutable.HashSet()++array)
  val mutableMap:scala.collection.mutable.Map[String,Int] = scala.collection.mutable.Map()++map
  val trieMap  = makeTrieMap(array)

  def roundBy3(n:Double)= BigDecimal(n).setScale(3, BigDecimal.RoundingMode.HALF_UP).toDouble

  def makeTrieMap(array:Array[String]): scala.collection.concurrent.TrieMap[String,Int] ={
    val ct = new scala.collection.concurrent.TrieMap[String, Int]
    for (i <- 0 until array.length) ct.put(""+i, i)
    ct
  }

  def main(args: Array[String]) {

    val trieMapTime = standardConfig measure {
      trieMap.par
    }

    val trieMapTMap:Map[String,Double] = TreeMap("trie map" -> roundBy3(trieMapTime))

    val mutableMapTime = standardConfig measure {
      mutableMap.par
    }

    val mutableTMap:Map[String,Double] = TreeMap("mutable map" -> roundBy3(mutableMapTime))++trieMapTMap

    val mutableSetTime = standardConfig measure {
      mutableSet.par
    }

    val mutableSetMap:Map[String,Double] = TreeMap("mutable set" -> roundBy3(mutableSetTime))++mutableTMap


    val listtime = standardConfig measure {
      list.par
    }

    val listMap:Map[String,Double] = TreeMap("list" -> roundBy3(listtime))++mutableSetMap

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
    trie map, time: 0.002
    vector, time: 0.002
    mutable map, time: 0.007
    mutable set, time: 0.017
    set, time: 0.092
    map, time: 0.11
    array, time: 6.422
    list, time: 148.587
    */
    println("\ntrieMap.size: "+trieMap.size)
  }

}
