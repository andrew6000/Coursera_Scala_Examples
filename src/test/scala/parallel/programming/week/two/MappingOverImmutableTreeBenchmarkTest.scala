package parallel.programming.week.two

import org.scalameter._


object MappingOverImmutableTreeBenchmarkTest {

  val threshold = 2000

  lazy val logE = math.log(math.E)
  def power(x: Double, p: Double): Int = {
    math.exp(p * math.log(math.abs(x)) / logE).toInt
  }

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 30,
    Key.exec.maxWarmupRuns -> 100,
    Key.exec.benchRuns -> 100,
    Key.verbose -> false
  ) withWarmer(new Warmer.Default)

  def makeTree(len: Int) : TreeMap.Tree[Double] = {
    if (len < threshold)
      TreeMap.Leaf((0 until len).map((x:Int) => (x % 100)*0.9).toArray)
    else {
      TreeMap.Node(makeTree(len/2), makeTree(len - len/2))
    }
  }

  def main(args: Array[String]) {
    val p = 1.5

    def f(x: Double) = power(x, p)

    val alen = 2000000
    val t = makeTree(alen)
    var t1: TreeMap.Tree[Double] = t
    var t2: TreeMap.Tree[Double] = t

    val seqtime = standardConfig measure {
      t1 = TreeMap.mapTreeSeq(t, f)
    }

    val partime = standardConfig measure {
      t2 = TreeMap.mapTreePar(t, f)
    }

    println(s"sequential time: $seqtime ms")
    println(s"parallel time: $partime ms")
    println(s"speedup time: ${ seqtime/partime}")
  }

}
