package parallel.programming.week.four

import org.scalameter._

import scala.collection.mutable.ArrayBuffer
import scala.collection.parallel.Combiner
import scala.reflect.ClassTag


/** Efficient Combiner */
//Two phase constructions
//We use an internal intermediary datastructure ArrayBuffer, which has an
//amortized O(1) look up and insert method, and O(n) when the ArrayBuffer
//runs out of space and needs to be copied into a bigger ArrayBuffer
/**
  * p = 1, time = 29.735669183333332 ms
  * p = 2, time = 15.653199316666667 ms
  * p = 4, time = 9.453566216666669 ms     <-- memory bottle neck
  * p = 8, time = 14.249582699999998 ms
  *
  */
class ArrayCombiner[T <: AnyRef: ClassTag](val parallelism: Int) extends Combiner[T, Array[T]] {
  private var numElems = 0

  //nested ArrayBuffer to store the elements
  private val buffers = new ArrayBuffer[ArrayBuffer[T]]
  buffers += new ArrayBuffer[T]

  def +=(elem: T) = {
    buffers.last += elem
    numElems += 1
    this
  }

  //Runtime: O(P) where P is the number of processors
  def combine[N <: T, That >: Array[T]](that: Combiner[N, That]) = {
    (that: @unchecked) match {
      case that: ArrayCombiner[T] =>
        buffers ++= that.buffers
        numElems += that.numElems
        this
    }
  }

  def size = numElems

  def clear() = buffers.clear()

  private def copyTo(array: Array[T], from: Int, end: Int): Unit = {
    var i = from
    var j = 0
    while (i >= buffers(j).length) {
      i -= buffers(j).length
      j += 1
    }
    var k = from
    while (k < end) {
      array(k) = buffers(j)(i)
      i += 1
      if (i >= buffers(j).length) {
        i = 0
        j += 1
      }
      k += 1
    }
  }

  def result: Array[T] = {
    val step = math.max(1, numElems / parallelism)
    val array = new Array[T](numElems)
    val starts = (0 until numElems by step) :+ numElems
    val chunks = starts.zip(starts.tail)
    val tasks = for ((from, end) <- chunks) yield task {
      copyTo(array, from, end)
    }
    tasks.foreach(_.join())
    array
  }

}

object ArrayCombiner {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 60,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]) {
    val size = 1000000

    def run(p: Int) {
      val taskSupport = new collection.parallel.ForkJoinTaskSupport(
        new scala.concurrent.forkjoin.ForkJoinPool(p))
      val strings = (0 until size).map(_.toString)
      val time = standardConfig measure {
        val parallelized = strings.par
        parallelized.tasksupport = taskSupport
        def newCombiner = new ArrayCombiner(p): Combiner[String, Array[String]]
        parallelized.aggregate(newCombiner)(_ += _, _ combine _).result
      }
      println(s"p = $p, time = $time ms")
    }

    run(1)
    run(2)
    run(4)
    run(8)
  }

}