package parallel.programming.week.two

import org.scalameter._



object ScanTreeBenchmarkTest {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 30,
    Key.exec.maxWarmupRuns -> 100,
    Key.exec.benchRuns -> 100,
    Key.verbose -> false
  ) withWarmer(new Warmer.Default)

  val plus = (x: Int, y: Int) => x + y
  val threshold = 700

  def makeTree(len:Int): PrefixScan.Tree[Int] = {

    if (len < threshold){

      PrefixScan.Node(PrefixScan.Leaf(1),
        PrefixScan.Node(PrefixScan.Leaf(2),
          PrefixScan.Node(PrefixScan.Node(PrefixScan.Leaf(24), PrefixScan.Leaf(30)),
            PrefixScan.Node(PrefixScan.Leaf(3), PrefixScan.Node(PrefixScan.Leaf(10), PrefixScan.Leaf(52))))))
    }else{

      PrefixScan.Node(makeTree(len/2), makeTree(len - len/2))
    }
  }

  def main(args: Array[String]): Unit = {

    val alen = 2000
    val t = makeTree(alen)
    var t1: PrefixScan.Tree[Int] = t
    var t2: PrefixScan.Tree[Int] = t

    val seqtime = standardConfig measure {

      println(PrefixScan.reduceRes(t1, plus))
    }

    val upSweepTime = standardConfig measure {

      println(PrefixScan.reduceRes(t1, plus))
    }

    println(s"sequential time: $seqtime ms")
    println(s"parallel upsweep time: $upSweepTime ms")
    println(s"speedup time: ${ seqtime/upSweepTime}")
  }

}
