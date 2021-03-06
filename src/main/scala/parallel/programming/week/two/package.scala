package parallel.programming.week

import java.util.concurrent.{ForkJoinPool, ForkJoinTask, ForkJoinWorkerThread, RecursiveTask}

import scala.util.DynamicVariable

package object two {

  private val sort1 = {
    val method = scala.util.Sorting.getClass.getDeclaredMethod("sort1", classOf[Array[Int]], classOf[Int], classOf[Int])
    method.setAccessible(true)
    (xs: Array[Int], offset: Int, len: Int) => {
      method.invoke(scala.util.Sorting, xs, offset.asInstanceOf[AnyRef], len.asInstanceOf[AnyRef])
    }
  }

  lazy val logE = math.log(math.E)
  def power(x: Int, p: Double): Int = {
    math.exp(p * math.log(math.abs(x)) / logE).toInt
  }

  def quickSort(xs: Array[Int], offset: Int, length: Int): Unit = {
    sort1(xs, offset, length)
  }

  val forkJoinPool = new ForkJoinPool

  abstract class TaskScheduler {
    def schedule[T](body: => T): ForkJoinTask[T]
    def parallel[A, B](taskA: => A, taskB: => B): (A, B) = {
      val right = task {
        taskB
      }
      val left = taskA
      (left, right.join())
    }
  }

  class DefaultTaskScheduler extends TaskScheduler {
    def schedule[T](body: => T): ForkJoinTask[T] = {
      val t = new RecursiveTask[T] {
        def compute = body
      }
      Thread.currentThread match {
        case wt: ForkJoinWorkerThread =>
          t.fork()
        case _ =>
          forkJoinPool.execute(t)
      }
      t
    }
  }

  val scheduler =
    new DynamicVariable[TaskScheduler](new DefaultTaskScheduler)

  /** from the common package
    *  task takes one by-name parameter, body */
  def task[T](body: => T): ForkJoinTask[T] = {
    scheduler.value.schedule(body)
  }

  /** from the common package */
  def parallel[A, B](taskA: => A, taskB: => B): (A, B) = {
    scheduler.value.parallel(taskA, taskB)
  }

  def parallel[A, B, C, D](taskA: => A, taskB: => B, taskC: => C, taskD: => D): (A, B, C, D) = {
    val ta = task { taskA }
    val tb = task { taskB }
    val tc = task { taskC }
    val td = taskD
    (ta.join(), tb.join(), tc.join(), td)
  }

}
