package parallel.programming.week.two

object SequentialMapOverArray {

  //A sequential map of array producing an array
  def mapASegSeq[A,B](inp: Array[A], left: Int, right: Int,
                      f: A => B, out: Array[B]) = {
    //Writes to out(i) for left <= i <= right-1
    var i = left
    while(i < right) {
      out(i) = f(inp(i))
      i += 1
    }
  }

}
