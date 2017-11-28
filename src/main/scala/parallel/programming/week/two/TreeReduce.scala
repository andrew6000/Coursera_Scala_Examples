package parallel.programming.week.two

object TreeReduce {

  sealed abstract class Tree[A]
  case class Leaf[A](value: A) extends Tree[A]
  case class Node[A](l: Tree[A], r: Tree[A]) extends Tree[A] {
    override def toString = s"{" + l + " | " + r + "}"
  }

  def reduce[A](t: Tree[A], f: (A,A) => A): A = t match {
    case Leaf(v) => v
    case Node(l,r) => f(reduce(l,f), reduce(r, f))
  }

  object Tree {
    def apply[A](v: A): Leaf[A] = new Leaf(v)
    def apply[A](l: Tree[A], r: Tree[A]): Tree[A] = new Node(l, r)
  }

}
