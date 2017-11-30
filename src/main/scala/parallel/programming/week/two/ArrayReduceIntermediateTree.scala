package parallel.programming.week.two

object ArrayReduceIntermediateTree {

  sealed abstract class TreeResA[A] { val res: A }
  case class LeafA[A](from: Int, to: Int,
                     override val res: A) extends TreeResA[A]
  case class NodeA[A](l: TreeResA[A],
                     override val res: A,
                     r: TreeResA[A]) extends TreeResA[A]

  var threshold = 1000

  def upsweep[A](inp: Array[A], from: Int, to: Int,
                 f: (A,A) => A): TreeResA[A] = {
    if (to - from < threshold)
      LeafA(from, to, reduceSeg1(inp, from + 1, to, inp(from), f))
    else {
      val mid = from + (to - from)/2
      val (tL,tR) = parallel(upsweep(inp, from, mid, f),
        upsweep(inp, mid, to, f))
      NodeA(tL, f(tL.res,tR.res), tR)
    }
  }

  def reduceSeg1[A](inp: Array[A], left: Int, right: Int,
                    a0: A, f: (A,A) => A): A = {
    var a= a0
    var i= left
    while (i < right) {
      a= f(a, inp(i))
      i= i+1
    }
    a
  }

}
