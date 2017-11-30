package parallel.programming.week.two

import scala.annotation.tailrec

object ParallelMapOverArray {

  val threshold = 10000                     //> threshold  : Int = 10000

  def mapASegPar[A,B](inp: Array[A], left: Int, right: Int,
                             f: A => B, out: Array[B]): Unit = {
    //Write to out(i) for left <= i <= right - 1
    if(right - left < threshold) {  //Threshold needs to be large enough,
                                    // otherwise we lose efficiency
      SequentialMapOverArray.mapASegSeq(inp, left, right, f, out)
    } else {
      val mid = left + (right-left) / 2
      parallel(mapASegPar(inp, left, mid, f, out), mapASegPar(inp, mid, right, f, out))
    }
  }

  def mapASegPar2[A,B](inp: Array[A], left: Int, right: Int,
                      f: (Int,A) => B, out: Array[B]): Unit = {
    //Write to out(i) for left <= i <= right - 1
    if(right - left < threshold) {  //Threshold needs to be large enough,
      // otherwise we lose efficiency
      SequentialMapOverArray.mapASegSeq2(inp, left, right, f, out)
    } else {
      val mid = left + (right-left) / 2
      parallel(mapASegPar2(inp, left, mid, f, out), mapASegPar2(inp, mid, right, f, out))
    }
  }

}
