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

  def x = 10
  def y = 9
  def z = 8

  def f3(x:Int,y:Int) = x+y


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

 /*   Although commutativity of f alone does not imply associativity, it implies
      it if we have an additional property. Define:
      E(x,y,z) = f(f(x,y), z)
    We say arguments of E can rotate if E(x,y,z) = E(y,z,x), that is:
      f(f(x,y), z) = f(f(y,z), x)
    Claim: if f is commutative and arguments of E can rotate then f is also
    associative.
      Proof:
      f(f(x,y), z) = f(f(y,z), x) = f(x, f(y,z))*/
    val associativeIfCommutative = f3(f3(x,y), z) == f3(f3(y,z), x)
    println(associativeIfCommutative)
    assert(associativeIfCommutative)

    val associativeIfCommutative2 = (f3(f3(x,y), z) == f3(f3(y,z), x) /*== f3(x, f3(y,z))*/ )
    println(associativeIfCommutative2)
    assert(associativeIfCommutative2)

    val associativeIfCommutative3 = (f3(f3(y,z), x) == f3(x, f3(y,z)))
    println(associativeIfCommutative3)
    assert(associativeIfCommutative3)
  }
}
