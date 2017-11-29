

val e = 1e-200                                  //> e  : Double = 1.0E-200
val x = 1e200                                   //> x  : Double = 1.0E200
val mx = -1.0e200                               //> mx  : Double = -1.0E200

(x + mx) //(1.0E200 + -1.0E200) = 0.0
(x + mx) + e  //0.0 + 1.0E-200                   //> res1: Double = 1.0E-200
(mx + e) //(-1.0E200 + 1.0E-200) = -1.0E200
x + (mx + e)  // 1.0E200 + -1.0E200              //> res17: Double = 0.0

/** Commutative vs. Associative */
//Floating point is commutative but not associative
(x + mx) + e == x + (mx + e)                    //> res18: Boolean = false


//Floating point multiplication is also not associative
e* (x * x)                                      //> res19: Double = Infinity
(e * x) * x                                     //> res20: Double = 1.0E200
(e * x) * x == e * (x * x)                      //> res21: Boolean = false