package parallel.programming.week

package object one {

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

}
