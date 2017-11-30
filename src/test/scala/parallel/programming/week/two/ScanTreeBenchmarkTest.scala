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
  val threshold = 20

  def makeTree(len:Int): PrefixScan.Tree[Int] = {

    PrefixScan.Node(PrefixScan.Node(PrefixScan.Leaf(1), PrefixScan.Leaf(3)), PrefixScan.Node(PrefixScan.Leaf(8), PrefixScan.Leaf(50)))

  }

  def main(args: Array[String]): Unit = {

    val alen = 200
    val t = makeTree(alen)
    var t1: PrefixScan.Tree[Int] = t
    var t2: PrefixScan.Tree[Int] = t

    val seqtime = standardConfig measure {

      println(PrefixScan.reduceRes(t1, plus))
    }

    println(s"sequential time: $seqtime ms")
  }

}
