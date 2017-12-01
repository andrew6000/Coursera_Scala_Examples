package parallel.programming.week.two

object ParallelScanLeft {

 def scanLeftViaMapReduce[A:Manifest](inp: Array[A], a0: A, f: (A,A) => A, out: Array[A]) = {
    val fi = (i: Int, v: A) => ParallelArrayReduce.reduceSeg3(inp, 0, i, a0, f)
    ParallelMapOverArray.mapASegPar2(inp, 0, inp.length, fi, out)
    val last = inp.length - 1
    out(last + 1) = f(out(last), inp(last))
  }

}
