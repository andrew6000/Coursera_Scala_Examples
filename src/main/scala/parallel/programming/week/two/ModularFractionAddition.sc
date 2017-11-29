

import math.Numeric.IntIsIntegral.plus

val x1 = 9
val x2 = 8
val x3 = 1
val y1 = 7
val y2 = 6
val y3 = 2

/*plus(x1*x2, y1*y2)
(x1*y2 + x2*y1, y1*y2)

plus(x1*y1, x2*y2)
(x1*y2 + x2*y1, y1*y2)*/


((x1*y2 + x2*y1)*y3 + x3*y1*y2, y1*y2*y3) == (x1*y2*y3 + x2*y1*y3 + x3*y1*y2, y1*y2*y3)