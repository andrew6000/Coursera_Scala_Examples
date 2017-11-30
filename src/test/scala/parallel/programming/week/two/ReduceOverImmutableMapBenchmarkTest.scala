package parallel.programming.week.two

import org.scalameter.{Key, Warmer, config}

object ReduceOverImmutableMapBenchmarkTest {

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

  def makeTree() : TreeReduce.Tree[Int] = {
    TreeReduce.Node(TreeReduce.Leaf(1), TreeReduce.Node(TreeReduce.Leaf(3), TreeReduce.Leaf(8)))
  }

  def main(args: Array[String]) {
    def fPlus = (x: Int, y: Int) => x + y
    def fMinus = (x: Int, y: Int) => x - y// not associative
    val t = makeTree()
    println(t)


    val seqtime = standardConfig measure {

      TreeReduce.reduce(t,fMinus)
    }

    val partime = standardConfig measure {
      TreeReduce.parReduce(t,fMinus)
    }

    println(s"sequential time: $seqtime ms")
    println(s"parallel time: $partime ms")
    println(s"speedup time: ${ seqtime/partime}")
  }

}
