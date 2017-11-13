object test {



  def foldLeft(lastResult: Int)(list: List[Int])(f: (Int, Int) => Int): Int = list match {
    case Nil => lastResult
    case x :: xs => {
      val result = f(lastResult, x)
      println(s"last: $lastResult, x: $x, result = $result")
      foldLeft(result)(xs)(f)
    }
  }
}
