package parallel.programming.week.two

object AverageWithReduce {

  def sum(arr:Array[Int]) = ParallelArrayReduce.reduce(arr,(x:Int,y:Int)=>x+y)
  def length(arr:Array[Int]) = ParallelArrayReduce.reduce(arr.map(x=>1),(x:Int,y:Int)=>x+y)
  def average(arr:Array[Int]) = sum(arr)/length(arr)

  def main(args: Array[String]): Unit = {

    val arr = Array.fill(100)(100).map(scala.util.Random.nextInt)
    println(average(arr))
  }

}
