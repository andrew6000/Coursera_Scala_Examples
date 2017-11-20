package parallel.programming.week.one


object SumPowersOfArraySegment {

  def main(args:Array[String]): Unit ={

    var arr: Array[Int] = Array(2,4,6,8,7,5,9,11,13,15)

    println(s"pNorm(arr,2) ${time {pNorm(arr,2)} }")
    println(s"pNormTwoPart(arr,2) ${time {pNormTwoPart(arr,2)} }")
    println(s"pNormTwoPartParallel(arr,2) ${time {pNormTwoPartParallel(arr,2)} }")
    println(s"pNormFourPartParallel(arr,2) ${time {pNormFourPartParallel(arr,2)} }")
    println(s"pNormRec(arr,2) ${time {pNormRec(arr,2)} }")

  }

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    print("...Elapsed time: " + (t1 - t0) + "ns  ")
    result
  }

  def sumSegment(a: Array[Int], p: Double, s: Int, t: Int): Int = {
    var i=s;
    var sum: Int = 0
    while(i<t) {
      sum = sum + power(a(i),p)
      i +=1
    }
    sum
  }                                         //> sumSegment2: (a: Array[Int], p: Double, s: Int, t: Int)Int
  def power(x: Int, p: Double): Int = math.exp(p*math.log(math.abs(x))).toInt


  def pNorm(a: Array[Int], p: Double): Int = power(sumSegment(a,p,0,a.length),1/p)

  def pNormTwoPart(a: Array[Int], p: Double): Int = {
    val m = a.length/2
    val (sum1, sum2) = (sumSegment(a, p, 0, m), sumSegment(a, p, m, a.length))

    power(sum1+sum2, 1/p)
  }

  /*
  * Behaves as an identity function
  *
  *
    Observe that parallel takes its arguments by name which is indicated by these two arrows
    in the signature of the function.


  In order to obtain the benefits of parallelism we need, in fact to indicate that we are
  passing not the value of these expressions but in fact the computations themselves.
  * */
  def parallel[A, B](taskA: => A, taskB: => B): (A,B) = (taskA, taskB)

  def pNormTwoPartParallel(a: Array[Int], p: Double): Int = {
    val m = a.length/2
    val (sum1, sum2) = parallel(sumSegment(a, p, 0, m), sumSegment(a,p,m,a.length))

    power(sum1+sum2, 1/p)
  }

  def pNormFourPartParallel(a: Array[Int], p: Double): Int = {
    val mid1 = a.length/4
    val mid2 = a.length/2
    val mid3 = a.length/2+a.length/4

    val((part1, part2), (part3, part4)) =
      parallel(
        parallel(sumSegment(a, p, 0, 0), sumSegment(a, p, mid1, mid2)),
        parallel(sumSegment(a, p, mid2, mid3), sumSegment(a, p, mid3, a.length)))
    power(part1+part2+part3+part4, 1/p)
  }

  val threshold = 2
  def segmentRec(a: Array[Int], p: Double, s: Int, t: Int): Int = {
    if(t-s < threshold)
      sumSegment(a,p,s,t)
    else {
      val m = s + (t-s)/2
      val (sum1, sum2) = parallel(segmentRec(a,p,s,m), segmentRec(a,p,m,t))

      sum1+sum2
    }
  }

  def pNormRec(a:Array[Int], p:Double): Int = power(segmentRec(a,p,0,a.length),1/p)
}
