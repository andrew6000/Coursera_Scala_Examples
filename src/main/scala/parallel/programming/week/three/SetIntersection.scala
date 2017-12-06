package parallel.programming.week.three

import scala.collection.{GenSet, Set, mutable}

object SetIntersection {

  def intersectionWrong(a: GenSet[Int], b: GenSet[Int]): Set[Int] = {
    val result = mutable.Set[Int]()
    for (x <- a) if (b contains x) result += x
    result
  }

  def intersection2(a: GenSet[Int], b: GenSet[Int]): GenSet[Int] = {
    if (a.size < b.size) a.filter(b(_))
    else b.filter(a(_))
  }

  import java.util.concurrent._
  def intersection1(a: GenSet[Int], b: GenSet[Int]) = {
    val result = new ConcurrentSkipListSet[Int]()
    for (x <- a) if (b contains x) result.add(x)
    result
  }

}
