package parallel.programming.week.two

import org.scalameter.{Key, Warmer, config}

object MappingOverCollectionsBenchmarkTest {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 100,
    Key.exec.benchRuns -> 100,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def f = (x: Int) => x*x

  def initializeArray(xs: Array[Int]) {
    var i = 0
    while (i < xs.length) {
      xs(i) = i % 100
      i += 1
    }
  }

  def initializeList(length: Int):List[Int] =  {
    Range(0,length).toList
  }

  def main(args: Array[String]): Unit = {

    val length = 1000000
    val xs = new Array[Int](length)
    val ls = initializeList(length)

    /*val timeMapSeq = standardConfig setUp {
      _ => val list = initializeList(length)
    } measure {

      SequentialMapOverList.mapSeq(ls,f)
    }*/

    val timeMapASegSeq = standardConfig setUp {
      _ => val list = initializeArray(xs)
    } measure {

      val out1 = new Array[Int](length)
      SequentialMapOverArray.mapASegSeq(xs, 0, xs.length, f, out1)
    }

    val timeMapASegPar = standardConfig setUp {
      _ => val list = initializeArray(xs)
    } measure {

      val out1 = new Array[Int](length)
      ParallelMapOverArray .mapASegPar(xs, 0, xs.length, f, out1)
    }

    //println(s"sequential map over list time: $timeMapSeq  ms")
    println(s"sequential map over array time: $timeMapASegSeq  ms")
    //println(s"speedup of array vs. list: ${timeMapSeq / timeMapASegSeq}")

    println(s"\nparallel map over array time: $timeMapASegPar  ms")
    println(s"speedup of parallel vs. sequentional array mapping: ${timeMapASegSeq / timeMapASegPar}")

  }

}
