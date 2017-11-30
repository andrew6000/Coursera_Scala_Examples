package parallel.programming.week.two

object RelativisticVelocityAddition {

  def fRelInt(u: Int, v: Int): Int = (u + v)/(1 + u*v)

  def errInt(lst:List[Int]): Int = lst.reduceLeft(fRelInt) - lst.reduceRight(fRelInt)

  def main(args: Array[String]): Unit = {

    val u=1
    val v=2
    val w=3

    println(fRelInt(fRelInt(u,v),w) == fRelInt(fRelInt(v,w),u))
    assert(fRelInt(fRelInt(u,v),w) == fRelInt(fRelInt(v,w),u))

    println(fRelInt(fRelInt(w,v),u) == fRelInt(fRelInt(v,w),u))
    assert(fRelInt(fRelInt(w,v),u) == fRelInt(fRelInt(v,w),u))

    println(errInt(Array.fill(100)(100000000).map(scala.util.Random.nextInt).toList))
  }

}
