package functional.programming.week.one

import org.scalameter.{Key, Warmer, config}

object BalanceParenthesesBenchmarkTest {

  val str1 = "((if (zero? x) max (/ 1 x))".toCharArray
  val str2 = "I told him (that it's not (yet) done). (But he wasn't listening)".toCharArray
  val str3 = "(o_()".toCharArray
  val str4 = ":-)".toCharArray
  val str5 = "())(".toCharArray
  val strs  = List(str1,str2,str3,str4,str5)

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 1000,
    Key.verbose -> true
  ) withWarmer (new Warmer.Default)


  def main(args: Array[String]): Unit = {

    val timeSeqBalance1 = standardConfig measure {

      strs.foreach(BalanceParentheses.balance1(_))
    }

    println(s"sequential balance1() time: $timeSeqBalance1 ms")
  }

}
