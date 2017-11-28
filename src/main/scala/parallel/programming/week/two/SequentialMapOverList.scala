package parallel.programming.week.two

object SequentialMapOverList {

  def mapSeq[A, B](l: List[A], f: A => B): List[B] = l match {
    case Nil => Nil
    case h :: t => f(h) :: mapSeq(t, f)
  }
}
