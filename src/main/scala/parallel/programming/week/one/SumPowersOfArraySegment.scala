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

  /*
  * The time bound on sequential execution time of this function is
    linear in the difference between t and s because we have an array and
    we are processing elements from s to t.

    For each element of the array, we are taking the constant amount of time
    which gives us some linear function of the form c1 (t- s) + c2. This constants
    c1 and c2 may be important in practice but are irrelevant for the asymptotic analysis,
    which only looks at the rate growth of the function describing the performance.

    W(s, t) = O(t - s)
  * */
  def sumSegment(a: Array[Int], p: Double, s: Int, t: Int): Int = {
    var i=s;
    var sum: Int = 0
    while(i<t) {
      sum = sum + power(a(i),p)
      i +=1
    }
    sum
  }

  val threshold = 2


  def segmentRec(a: Array[Int], p: Double, s: Int, t: Int):Int = {
    /*when the difference between the bounds is below some threshold,
    then we will invoke our previous sumSegment function */
    if (t - s < threshold) {
      sumSegment(a, p, s, t)
    } else {
      /*We will compute the middle element of the array and then invoke
      ourselves recursively with the intervals s to m and m to t.

      running time of the recursive function can be described using a computation tree

      the computation reduces to two recursive calls, one that takes parameters s and m1
      where m1 is the middle. And the other one takes parameters m1 and t.

      And this decomposition continues until the length of our segment
      is below the threshold. So computation over recursive function
      can be described using such tree.
      */
      val m= s + (t - s)/2
      val (sum1, sum2)= (segmentRec(a, p, s, m), segmentRec(a, p, m, t))
      sum1 + sum2
    }
  }


 /*
  def segmentRec(a: Array[Int], p: Double, s: Int, t: Int): Int = {
    if(t-s < threshold)
      sumSegment(a,p,s,t)
    else {
      val m = s + (t-s)/2
      val (sum1, sum2) = parallel(segmentRec(a,p,s,m), segmentRec(a,p,m,t))

      sum1+sum2
    }
  }*/

  def power(x: Int, p: Double): Int = math.exp(p*math.log(math.abs(x))).toInt


  def pNorm(a: Array[Int], p: Double): Int = power(sumSegment(a,p,0,a.length),1/p)

  def pNormTwoPart(a: Array[Int], p: Double): Int = {
    val m = a.length/2
    val (sum1, sum2) = (sumSegment(a, p, 0, m), sumSegment(a, p, m, a.length))

    power(sum1+sum2, 1/p)
  }



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



  def pNormRec(a:Array[Int], p:Double): Int = power(segmentRec(a,p,0,a.length),1/p)
}
