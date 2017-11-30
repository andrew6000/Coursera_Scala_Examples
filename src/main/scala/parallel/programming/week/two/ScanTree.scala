package parallel.programming.week.two

/** Trees storing our input collection only have values in leaves */
sealed abstract class ScanTree[A] {
  def apply(a: A) = new ScanLeaf(a)

  def apply(l: ScanTree[A], r: ScanTree[A]) = new ScanNode(l, r)
}
case class ScanLeaf[A](a: A) extends ScanTree[A]
case class ScanNode[A](l: ScanTree[A], r: ScanTree[A]) extends ScanTree[A]

/** Trees storing intermediate values also have res values in nodes */
//It's like the huffman encoding
sealed abstract class ScanTreeRes[A] {
  val res: A
  def apply[A](l: ScanTreeRes[A], res: A, r: ScanTreeRes[A]): ScanTreeRes[A] = {
    new ScanNodeRes(l, res, r)
  }
  def apply(from: Int, to: Int, res: A) = new ScanLeaf2(from, to, res)
  def apply(l: ScanTreeRes[A], res: A, r: ScanTreeRes[A]) = new ScanNode2(l, res, r)
  def apply[A](res: A): ScanTreeRes[A] = {
    new ScanLeafRes(res)
  }
}
case class ScanLeafRes[A](override val res: A) extends ScanTreeRes[A]
case class ScanNodeRes[A](l: ScanTreeRes[A],
                          override val res: A, r: ScanTreeRes[A]) extends ScanTreeRes[A]

//immediate tree for array reduce
case class ScanLeaf2[A](from: Int,
                        to: Int, override val res: A) extends ScanTreeRes[A]
case class ScanNode2[A](l: ScanTreeRes[A],
                        override val res: A, r: ScanTreeRes[A]) extends ScanTreeRes[A]
