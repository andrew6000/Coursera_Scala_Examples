package parallel.programming.week.two


object PrefixScan {
  sealed abstract class Tree[A]
  case class Leaf[A](a: A) extends Tree[A]
  case class Node[A](l: Tree[A], r: Tree[A]) extends Tree[A]

  sealed abstract class TreeRes[A] { val res : A }
  case class LeafRes[A](override val res: A) extends TreeRes[A]
  case class NodeRes[A](l : TreeRes[A], override val res: A, r: TreeRes[A]) extends TreeRes[A]

  def reduceResVerbose[A](t: Tree[A], f: (A, A) => A): TreeRes[A] = t match {
    case Leaf(v) =>
      println(s"Logical Processor Number: ${ProcessorNumberNative.CLibrary.INSTANCE.GetCurrentProcessorNumber()}")
      LeafRes(v)

    case Node(l, r) =>
      println(s"Logical Processor Number: ${ProcessorNumberNative.CLibrary.INSTANCE.GetCurrentProcessorNumber()}")
      val (tL, tR) = (reduceResVerbose(l, f), reduceResVerbose(r, f))
      NodeRes(tL, f(tL.res, tR.res), tR)
  }

  def reduceRes[A](t: Tree[A], f:(A,A)=>A): TreeRes[A] = t match {
    case Leaf(v) => LeafRes(v)
    case Node(l, r) => {
      val (tL, tR) = (reduceRes(l, f), reduceRes(r, f))
      NodeRes(tL, f(tL.res, tR.res), tR)
    }
  }

  def upsweep[A](t: Tree[A], f: (A,A) => A): TreeRes[A] = t match {
    case Leaf(v) => LeafRes(v)
    case Node(l, r) => {
      val (tL, tR) = parallel(upsweep(l, f), upsweep(r, f))
      NodeRes(tL, f(tL.res, tR.res), tR)
    }
  }
}