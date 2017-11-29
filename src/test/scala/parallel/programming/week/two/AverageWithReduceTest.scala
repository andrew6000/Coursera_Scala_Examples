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

  test("Scala API vs. handmade implementation with two parts"){

    val api = arr1.sum/arr1.length
    val one = AverageWithReduce.average(arr1)

    assert(api === one)
  }

  test("Scala API vs. handmade implementation with one part"){

    val api = arr1.sum/arr1.length
    val two =  AverageWithReduce.average2(arr1)

    assert(api === two)
  }


  test("handmade implementation with one part vs. two part"){

    val one = AverageWithReduce.average(arr1)
    val two =  AverageWithReduce.average2(arr1)

    assert(one === two)
  }

}
