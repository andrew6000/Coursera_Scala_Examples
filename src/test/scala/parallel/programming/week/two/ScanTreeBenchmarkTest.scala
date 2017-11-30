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

  def makeTree(len:Int): PrefixScan.ScanTree[Int] = {

    //if (len < threshold){

    PrefixScan.ScanNode(PrefixScan.ScanNode(PrefixScan.ScanLeaf(1), PrefixScan.ScanLeaf(3)), PrefixScan.ScanNode(PrefixScan.ScanLeaf(8), PrefixScan.ScanLeaf(50)))
    /*}else{

      PrefixScan.Node(makeTree(len/2), makeTree(len - len/2))
    }*/
  }

  def main(args: Array[String]): Unit = {

    val alen = 2000
    val t = makeTree(alen)
    var t1: PrefixScan.ScanTree[Int] = t

    var t2: PrefixScan.TreeRes[Int] = PrefixScan.LeafRes(5)
    var t3: PrefixScan.ScanTree[Int] = PrefixScan.ScanLeaf(5)
    var t4: PrefixScan.ScanTree[Int] = PrefixScan.ScanLeaf(5)

    val seqtime = standardConfig measure {

      println(PrefixScan.reduceRes(t1, plus))
    }

    val upSweepTime = standardConfig measure {

      t2 =PrefixScan.upsweep(t1, plus)
    }

    val DownSweepTime = standardConfig measure {

      t3 = PrefixScan.downsweep(t2, 100,plus)
    }

    val ScanLeftTime = standardConfig measure {

      t4 = PrefixScan.scanLeft(t1,100, plus)
    }

    println(s"sequential time: $seqtime ms")
    println(s"parallel upsweep time: $upSweepTime ms")
    println(s"parallel downsweep time: $DownSweepTime ms")
    println(s"parallel upsweep time: $ScanLeftTime ms")

    println(t2)
    println(t3)
    println(t4)
  }

}
