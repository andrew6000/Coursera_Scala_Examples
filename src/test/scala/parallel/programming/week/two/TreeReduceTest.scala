package parallel.programming.week.two

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

class TreeReduceTest extends FunSuite with BeforeAndAfter {

  var tree:TreeReduce.Tree[Int] = _
  var tree2:TreeReduce.Tree[Int] = _

  before {
    tree = makeTree()
    tree2 = makeTree2()
  }

  def fPlus = (x: Int, y: Int) => x + y

  def makeTree() : TreeReduce.Tree[Int] = {
    TreeReduce.Node(TreeReduce.Leaf(1), TreeReduce.Node(TreeReduce.Leaf(3), TreeReduce.Leaf(8)))
  }

  def makeTree2() : TreeReduce.Tree[Int] = {
    TreeReduce.Node(TreeReduce.Node(TreeReduce.Leaf(1), TreeReduce.Leaf(3)),TreeReduce.Leaf(8))
  }

  test("1.) map/reduce combined to equal toList") {
    assert(tree.toList == TreeReduce.reduce(tree.map(List(_)), (a:List[Int],b:List[Int])=> a++b))
  }

  test("2.) map/reduce combined to equal toList") {
    assert(tree2.toList == TreeReduce.reduce(tree2.map(List(_)), (a:List[Int],b:List[Int])=> a++b))
  }

  test("associativity") {
    //at 14:00 in Week 2 Parallel Fold (Reduce) Operation Video

    /*Consequence (Scala): if f : (A,A)=>A is associative, t1:Tree[A] and
      t2:Tree[A] and if toList(t1)==toList(t2), then:
      reduce(t1, f)==reduce(t2, f)*/

    assert((tree.toList == tree2.toList)
        && (TreeReduce.reduce(tree,fPlus) == TreeReduce.reduce(tree2,fPlus)))
  }


}
