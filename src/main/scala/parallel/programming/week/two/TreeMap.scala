package parallel.programming.week.two

object TreeMap {


  sealed abstract class Tree[A] { val size: Int }

  case class Leaf[A](a: Array[A]) extends Tree[A] {
    override val size = a.size
  }
  case class Node[A](l: Tree[A], r: Tree[A]) extends Tree[A] {
    override val size = l.size + r.size
  }

  def mapTreeSeq[A:Manifest,B:Manifest](t: Tree[A], f: A => B) : Tree[B] = t match {
    case Leaf(a) => {
      val len = a.length
      val b = new Array[B](len)
      var i= 0
      while (i < len) {
        b(i)= f(a(i))
        i= i + 1
      }
      Leaf(b)
    }
    case Node(l,r) => {
      val (lb,rb) = (mapTreeSeq(l,f),mapTreeSeq(r,f))
      Node(lb, rb)
    }
  }

  def mapTreePar[A:Manifest,B:Manifest](t: Tree[A], f: A => B) : Tree[B] = t match {
    case Leaf(a) => {
      val len = a.length
      val b = new Array[B](len)
      var i= 0
      while (i < len) {
        b(i)= f(a(i))
        i= i + 1
      }
      Leaf(b)
    }
    case Node(l,r) => {
      val (lb,rb) = parallel(mapTreePar(l,f),mapTreePar(r,f))
      Node(lb, rb)
    }
  }
}
