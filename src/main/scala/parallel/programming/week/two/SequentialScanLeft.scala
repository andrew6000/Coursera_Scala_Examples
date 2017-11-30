package parallel.programming.week.two

object SequentialScanLeft {

  def scanLeftSeq[A: Manifest](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A]) = {
    val len = inp.length
    var a = a0
    out(0) = a
    var i = 0
    while(i < len) {
      a = f(a, inp(i))
      i += 1
      out(i) = a
    }
  }

}
