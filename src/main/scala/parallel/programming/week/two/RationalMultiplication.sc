

def times(x: (Int, Int), y: (Int, Int)) = math.Numeric.IntIsIntegral.times(x._1*x._2, y._1*y._2)

val x1 = 9
val x2 = 5
val y1 = 3
val y2 = 7

val t1 = times((x1,y1),(x2,y2))
val t2 =   (x1*x2, y1*y2)

t1 == t2._1*t2._2