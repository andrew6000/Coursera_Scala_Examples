import functional.design.week.four.frp.{BankAccountSignal, Signal, Var}



type Position = (Int)

/*
A signal of position, which at any point in time represents the current mouse position.
Hence, a function from the domain of time values to this curve.
A function from time values to positions.*/

def mousePosition = Signal(3)
mousePosition()

/** Fundamental Signal Operation
given a rectangle defined by LL (lower left) corner and UR (upper right) corner,
we check if the mousePos is inside the rectangle or not inside the rectangle*/
def inRectangle(LL: Position, UR: Position): Signal[Boolean] = {
  Signal {
    val pos = mousePosition()

    // a discrete signal with two states
    LL <= pos && pos <= UR
  }
}


var a = 2                                 //> a  : Int = 2
var b = 2 * a                             //> b  : Int = 4
a = a + 1
b //still 4. Not automatically updated to 9


val aSig: Var[Int] = Var(2)                     //> aSig  : week4.Var[Int] = week4.Var@60addb54
val bSig: Var[Int] = Var(2*aSig())              //> bSig  : week4.Var[Int] = week4.Var@3f2a3a5
val bVal: Int = bSig()  //to "dereference" the signal
//> bVal  : Int = 4
aSig.update(3)
bSig()  //We get 6!                             //> res6: Int = 6


/** BankAccount Signal */
def consolidated(accts: List[BankAccountSignal]): Signal[Int] = {
  Signal(accts.map(_.balance()).sum) //a signal of sum of all accounts' balance
}


val aAcc, bAcc = new BankAccountSignal()  //> aAcc  : week4.BankAccountSignal = week4.BankAccountSignal@4cb2c100
//| bAcc  : week4.BankAccountSignal = week4.BankAccountSignal@6fb554cc
val c = consolidated(List(aAcc, bAcc))    //> c  : week4.Signal[Int] = week4.Signal@5cc7c2a6
c()                                       //> res7: Int = 0
aAcc deposit 20
c()                                       //> res8: Int = 20
bAcc deposit 30
c()                                       //> res9: Int = 50
val xchange = Signal(246.00)              //> xchange  : week4.Signal[Double] = week4.Signal@2344fc66
val inDollar = Signal(c()*xchange())      //> inDollar  : week4.Signal[Double] = week4.Signal@458ad742
inDollar()                                //> res10: Double = 12300.0
bAcc withdraw 10
inDollar()                                //> res11: Double = 9840.0

/** Caveat with Signals */
val num = Var(1)                          //> num  : week4.Var[Int] = week4.Var@5afa04c
val twice = Signal(num() * 2)             //> twice  : week4.Signal[Int] = week4.Signal@6ea12c19
num() = 2
twice()                                   //> res12: Int = 4
var num2 = Var(1)                         //> num2  : week4.Var[Int] = week4.Var@174d20a
val twice2 = Signal(num2() * 2)           //> twice2  : week4.Signal[Int] = week4.Signal@66d2e7d9
num2 = Var(2)
twice2()                                  //> res13: Int = 2