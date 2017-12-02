package parallel.programming.week.two

object AverageWithReduce {

  def sum(arr:Array[Int]) = ParallelArrayReduce.reduce(arr,(x:Int,y:Int)=>x+y)
  def length(arr:Array[Int]) = ParallelArrayReduce.reduce(arr.map(x=>1),(x:Int,y:Int)=>x+y)
  def average(arr:Array[Int]) = sum(arr)/length(arr)
  def f(a: (Int, Int), b: (Int, Int)) = (a._1+b._1,a._2+b._2)

  def average2(arr:Array[Int]) = {
   val(sum,length) = ParallelArrayReduce.reduce(arr.map(x => (x,1)), f)
    sum/length
  }

  def main(args: Array[String]): Unit = {

    val arr = Array.fill(100)(100).map(scala.util.Random.nextInt)
    println(arr.mkString(","))
    println(average(arr))
    println(average2(arr))
  }
}
