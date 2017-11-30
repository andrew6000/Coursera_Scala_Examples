package parallel.programming.week.two

object ArrayReduceIntermediateTree {

  sealed abstract class TreeResA[A] { val res: A }
  case class LeafA[A](from: Int, to: Int,
                     override val res: A) extends TreeResA[A]
  case class NodeA[A](l: TreeResA[A],
                     override val res: A,
                     r: TreeResA[A]) extends TreeResA[A]

}
