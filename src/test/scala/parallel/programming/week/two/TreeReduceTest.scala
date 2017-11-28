package parallel.programming.week.two

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

class TreeReduceTest extends FunSuite with BeforeAndAfter {

  var tree:TreeReduce.Tree[Int] = _

  before {
    tree = makeTree()
  }

  def makeTree() : TreeReduce.Tree[Int] = {
    TreeReduce.Node(TreeReduce.Leaf(1), TreeReduce.Node(TreeReduce.Leaf(3), TreeReduce.Leaf(8)))
  }
  test("map/reduce combined to equal toList") {
    assert(tree.toList == TreeReduce.reduce(tree.map(List(_)), (a:List[Int],b:List[Int])=> a++b))
  }


}
