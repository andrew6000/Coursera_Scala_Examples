package functional.design.week.two.water.pouring

import functional.design.week.two.water.pouring._

object PouringTest {

  def main(args: Array[String]): Unit ={

    val p =  PouringCommented(Vector(12, 8, 5), Vector(12, 0, 0))
    val result = p.solution(Vector(6, 6, 0)).toList
    println(result)
  }

}
