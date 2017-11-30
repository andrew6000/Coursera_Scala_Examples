package parallel.programming.week.two

object RelativisticVelocityAddition {

  def fRelInt(u: Int, v: Int): Int = (u + v)/(1 + u*v)

  def errInt(lst:List[Int]): Int = lst.reduceLeft(fRelInt) - lst.reduceRight(fRelInt)

  def f(u: Double, v: Double): Double = (u + v)/(1.0 + u*v)

  def err(lst:List[Double]): Double = lst.reduceLeft(f) - lst.reduceRight(f)

  /*
  * The method testAssoc computes the error between reduceLeft and reduceRight on a random
  * list of small numbers using the previously introduced fuction f.
  *
  * Run testAssoc several times and observe the result
  * */
  def testAssoc: Double = {
    val r = new scala.util.Random
    val lst = List.fill(400)(r.nextDouble*0.002)
    err(lst)
    //err(List(1,2,3))
  }

  def main(args: Array[String]): Unit = {

    val u=1
    val v=2
    val w=3

    /*
    Will not work
    val u=6
    val v=7
    val w=8
    */

    println(fRelInt(fRelInt(u,v),w) == fRelInt(fRelInt(v,w),u))
    assert(fRelInt(fRelInt(u,v),w) == fRelInt(fRelInt(v,w),u))

    println(fRelInt(fRelInt(w,v),u) == fRelInt(fRelInt(v,w),u))
    assert(fRelInt(fRelInt(w,v),u) == fRelInt(fRelInt(v,w),u))

   /* The arguments of this
    expression E, the arguments of E do rotate because of the rotation and commutativity,
    we obtain that f is associative.

    This was arguably slightly easier than
      directly checking associativity law. Now, it is tempting to implement such
      an operation using floating point numbers, and we can do that. But we have to keep in mind that floating
    point operations are not associative.

      So our calculations on this will not hold. And even though we could argue that
    the calculations will be only slightly off in one step*/

    //Run testAssoc several times and observe the result
    println(testAssoc)
  }

}
