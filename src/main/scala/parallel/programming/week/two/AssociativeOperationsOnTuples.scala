package parallel.programming.week.two


object AssociativeOperationsOnTuples {

  type A1 = Double
  type A2 = Float


  def f1(a: (A1, A1)) = a._1+ a._2
  def f2(a: (A2, A2)) = a._1 * a._2

  def x1 = 5
  def x2 = 6
  def y1 = 1
  def y2 = 2
  def z1 = 3
  def z2 = 4

  def f(a1: (A1, A2), a2: (A1, A2)) = (f1(a1._1, a2._1), f2(a1._2, a2._2))

  def main(args:Array[String]): Unit ={

    val basicAssociativity = f((x1, y1), (x2, y2)) == (f1(x1, x2), f2(y1, y2))

    println(basicAssociativity)
    assert(basicAssociativity)

    val deepAssociativity = {f(f((x1, x2), (y1, y2)), (z1, z2)) ==
      f((f1(x1,y1), f2(x2,y2)), (z1,z2)) ==
      (f1(f1(x1,y1), z1), f2(f2(x2,y2), z2)) ==
      (f1(x1, f1(y1,z1)), f2(x2, f2(y2,z2))) ==
      f((x1, x2), (f1(y1,z1), f2(y2,z2))) ==
      f((x1, x2), f((y1,y2), (z1, z2)))
    }

    println(deepAssociativity)
    //assert(deepAssociativity)      currently fails

    println(basicAssociativity == deepAssociativity)
    assert(basicAssociativity == deepAssociativity)
  }
}
