import functional.design.week.four.frp.{Signal, Var}



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