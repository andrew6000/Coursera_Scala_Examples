package functional.design.week.four.frp

object SignalTester {


  def main(args:Array[String]): Unit ={

    stateUpdateTest1()
  }


  def stateUpdateTest1(): Unit ={

    val aSig: Var[Int] = Var(2)                     //> aSig  : week4.Var[Int] = week4.Var@60addb54
    val bSig: Var[Int] = Var(2*aSig())              //> bSig  : week4.Var[Int] = week4.Var@3f2a3a5
    val bVal: Int = bSig()  //to "dereference" the signal
    //> bVal  : Int = 4
    aSig.update(3)
    bSig()  //We get 6!
  }

}
