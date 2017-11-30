package parallel.programming.week.two

object ArrayReduceIntermediateTree {

  sealed abstract class TreeResA[A] { val res: A }
  case class LeafA[A](from: Int, to: Int,
                     override val res: A) extends TreeResA[A]
  case class NodeA[A](l: TreeResA[A],
                     override val res: A,
                     r: TreeResA[A]) extends TreeResA[A]

  var threshold = 4000000

  def upsweep[A: Manifest](inp: Array[A], from: Int, to: Int,
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

  def downsweep[A: Manifest](inp: Array[A],
                   a0: A, f: (A,A) => A,
                   t: TreeResA[A],
                   out: Array[A]): Unit = t match {
    case LeafA(from, to, res) =>
      scanLeftSeg(inp, from, to, a0, f, out)
    case NodeA(l, _, r) => {
      val (_,_) = parallel(
        downsweep(inp, a0, f, l, out),
        downsweep(inp, f(a0,l.res), f, r, out))
    }
  }

  def reduceSeg1[A: Manifest](inp: Array[A], left: Int, right: Int,
                    a0: A, f: (A,A) => A): A = {
    var a= a0
    var i= left
    while (i < right) {
      a= f(a, inp(i))
      i= i+1
    }
    a
  }

  def scanLeft[A: Manifest](inp: Array[A],
                  a0: A, f: (A,A) => A,
                  out: Array[A]) = {
    val t = upsweep(inp, 0, inp.length, f)
    downsweep(inp, a0, f, t, out) // fills out[1..inp.length]
    out(0)= a0 // prepends a0
  }

  def scanLeftSeg[A: Manifest](inp: Array[A], left: Int, right: Int,
                     a0: A, f: (A,A) => A,
                     out: Array[A]) = {
    if (left < right) {
      var i= left
      var a= a0
      while (i < right) {
        a= f(a,inp(i))
        i= i+1
        out(i)=a
      }
    }
  }

}
