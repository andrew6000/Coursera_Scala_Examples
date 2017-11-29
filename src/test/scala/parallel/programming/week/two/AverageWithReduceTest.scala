package parallel.programming.week.two

import org.scalatest.{BeforeAndAfter, FunSuite}

class AverageWithReduceTest extends FunSuite with BeforeAndAfter{

  var arr1: Array[Int]=_
  var arr2: Array[Int]=_
  val length:Int = 700000

  before {

    arr1 = makeArray()
    arr2 = arr1.reverse
  }

  def makeArray(): Array[Int] ={

     Array.fill(10000)(length).map(scala.util.Random.nextInt)
  }

  test("Scala API vs. handmade implementation"){

    assert(arr1.sum/arr1.length == AverageWithReduce.average(arr1))
  }

}
