


//making an operation commutative is easy
//Patching function to make them commutative but we don't have patches to make them
//associative
def less(x: Double, y: Double): Boolean = x > y //> less: (x: Double, y: Double)Boolean
def g(a: Double, b: Double) = a + b             //> g: (a: Double, b: Double)Double
def f(x: Double, y: Double) = if(less(y,x)) g(y, x) else g(x,y)
//> f: (x: Double, y: Double)Double
val x=8
val y=5

f(x,y)  //> res0: Double = 13.0
f(y,x)  //> res1: Double = 13.0

(f(x,y) == f(y,x)) //> res2: Boolean = true
