package parallel.programming.week.three

object Fold {

  /** fold vs. foldLeft
    * def fold(z: A)(f: A, A) => A): A
    * def foldLeft(z: A)(f: (B, A) => B): B
    * def fold(z: A)(op: (A,A) => A): A = foldLeft[A](z)(op)
    */
  //To ensure correctness, the binary operator f MUST be associative
  //f(a, f(b,c)) == f(f(a,b),c)
  //f(z,a) == f(a,z) == a
  //We say that the neutral element z and the binary operator f must form a monoid
  /*
   * sum
   */
  def sum(xs: Array[Int]): Int = {
    xs.foldLeft(0)(_+_) //<- foldLeft cannot have data parallel
  }                                               //> sum: (xs: Array[Int])Int
  def parSum(xs: Array[Int]): Int = {
    xs.par.fold(0)(_+_)
  }

  def max(xs: Array[Int]): Int = {
    //instead of math.max, you can use (x, y) => if(x > y) x else y
    xs.par.fold(Int.MinValue)(math.max) //<- math.max is associative. This is important for fold
  }                                               //> max: (xs: Array[Int])Int


}
