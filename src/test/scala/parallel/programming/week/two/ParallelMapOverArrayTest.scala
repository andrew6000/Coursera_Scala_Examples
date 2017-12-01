package parallel.programming.week.two

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

class ParallelMapOverArrayTest  extends FunSuite with BeforeAndAfter{

  val length = 30
  var array:Array[Int] = new Array[Int](length)


  before{

    initializeArray(array)
  }

  def initializeArray(xs: Array[Int]) {
    var i = 0
    while (i < xs.length) {
      xs(i) = i
      i += 1
    }
  }

  def f = (x: Int) => x*x

  test("test parallel map") {

    val out1 = new Array[Int](length)
    ParallelMapOverArray .mapASegPar(array, 0, array.length, f, out1)
    println(out1.mkString(","))
  }
}
