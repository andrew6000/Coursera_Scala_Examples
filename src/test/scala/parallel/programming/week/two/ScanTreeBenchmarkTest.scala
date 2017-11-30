package parallel.programming.week.two

import org.scalameter._


object ScanTreeBenchmarkTest {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 30,
    Key.exec.maxWarmupRuns -> 100,
    Key.exec.benchRuns -> 100,
    Key.verbose -> false
  ) withWarmer(new Warmer.Default)

  def plus = (x: Int, y: Int) => x + y
  val threshold = 2000

  def makeTree(len:Int): ScanTree[Int] = {
    if (len < threshold){
     ScanNode(ScanNode(ScanLeaf(1), ScanLeaf(3)), ScanNode(ScanLeaf(8), ScanLeaf(50)))
    }else{
      ScanNode(makeTree(len/2), makeTree(len - len/2))
    }
  }

  def main(args: Array[String]): Unit = {

    val alen = 200000000
    val t = makeTree(alen)
    var t1: ScanTree[Int] = t
    var t2: ScanTree[Int] = t

    val seqtime = standardConfig measure {

      ScanTree.reduceRes(t1, plus)
    }

    println(s"sequential time: $seqtime ms")
  }

}
