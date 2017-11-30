package parallel.programming.week.two


object PrefixScan {
  sealed abstract class ScanTree[A] {
    def apply(a: A) = new ScanLeaf(a)

    def apply(l: ScanTree[A], r: ScanTree[A]) = new ScanNode(l, r)
  }
  case class ScanLeaf[A](a: A) extends ScanTree[A]
  case class ScanNode[A](l: ScanTree[A], r: ScanTree[A]) extends ScanTree[A]

  sealed abstract class TreeRes[A] {
    val res: A
    def apply[A](l: TreeRes[A], res: A, r: TreeRes[A]): TreeRes[A] = {
      new NodeRes(l, res, r)
    }
    def apply(from: Int, to: Int, res: A) = new ScanLeaf2(from, to, res)
    def apply(l: TreeRes[A], res: A, r: TreeRes[A]) = new ScanNode2(l, res, r)
    def apply[A](res: A): TreeRes[A] = {
      new LeafRes(res)
    }
  }
  case class LeafRes[A](override val res: A) extends TreeRes[A]
  case class NodeRes[A](l : TreeRes[A], override val res: A, r: TreeRes[A]) extends TreeRes[A]

  def reduceResVerbose[A](t: ScanTree[A], f: (A, A) => A): TreeRes[A] = t match {
    case ScanLeaf(v) =>
      println(s"Logical Processor Number: ${ProcessorNumberNative.CLibrary.INSTANCE.GetCurrentProcessorNumber()}")
      LeafRes(v)

    case ScanNode(l, r) =>
      println(s"Logical Processor Number: ${ProcessorNumberNative.CLibrary.INSTANCE.GetCurrentProcessorNumber()}")
      val (tL, tR) = (reduceResVerbose(l, f), reduceResVerbose(r, f))
      NodeRes(tL, f(tL.res, tR.res), tR)
  }

  //immediate tree for array reduce
  //immediate tree for array reduce
  case class ScanLeaf2[A](from: Int,
                          to: Int, override val res: A) extends TreeRes[A]
  case class ScanNode2[A](l: TreeRes[A],
                          override val res: A, r: TreeRes[A]) extends TreeRes[A]




  def reduceRes[A](t: ScanTree[A], f:(A,A)=>A): TreeRes[A] = t match {
    case ScanLeaf(v) => LeafRes(v)
    case ScanNode(l, r) => {
      val (tL, tR) = (reduceRes(l, f), reduceRes(r, f))
      NodeRes(tL, f(tL.res, tR.res), tR)
    }
  }

  def upsweep[A](t: ScanTree[A], f: (A,A) => A): TreeRes[A] = t match {
    case ScanLeaf(v) => LeafRes(v)
    case ScanNode(l, r) => {
      val (tL, tR) = parallel(upsweep(l, f), upsweep(r, f))
      NodeRes(tL, f(tL.res, tR.res), tR)
    }
  }

  def downsweep[A](t: TreeRes[A], a0: A, f : (A,A) => A): ScanTree[A] = t match {
    case LeafRes(a) => ScanLeaf(f(a0, a))
    case NodeRes(l, _, r) => {
      val (tL, tR) = parallel(downsweep[A](l, a0, f), downsweep[A](r, f(a0, l.res), f))
      ScanNode(tL, tR) }
  }

  def scanLeft[A](t: ScanTree[A], a0: A, f: (A,A) => A): ScanTree[A] = {
    val tRes = upsweep(t, f)
    val scan1 = downsweep(tRes, a0, f)
    prepend(a0, scan1)
  }

  def prepend[A](x: A, t: ScanTree[A]): ScanTree[A] = t match {
    case ScanLeaf(v) => ScanNode(ScanLeaf(x), ScanLeaf(v))
    case ScanNode(l, r) => ScanNode(prepend(x, l), r)
  }
}