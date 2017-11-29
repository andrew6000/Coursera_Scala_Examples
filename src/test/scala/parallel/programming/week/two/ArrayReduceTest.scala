package parallel.programming.week.two


import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import math.abs

class ArrayReduceTest extends FunSuite with BeforeAndAfter{

  var arr1: Array[Int]=_
  var arr2: Array[Int]=_
  val length:Int = 700000

  before {

    arr1 = makeArray()
    arr2 = arr1.reverse
  }

  def add(i:Int,j:Int) = {
    abs(i+j)
  }

  def makeArray(): Array[Int] ={

    Array.fill(10000)(10).map(scala.util.Random.nextInt)
  }

  test("Associativity 1 - using sum array norm") {

   /* Which combination of operations does sum of powers correspond to?
    reduce(map(a, power(abs(_), p)), _ + _)
    Here + is the associative operation of reduce
      map can be combined with reduce to avoid intermediate collections*/

    var result = ParallelArrayReduce.reduce(arr1.map(x => power(x,2)),add )
    var result2 = ParallelArrayReduce.reduce(arr2.map(x => power(x,2)),add )

    println(result)
    println(result2)

    assert(result2 === result)
  }

}
