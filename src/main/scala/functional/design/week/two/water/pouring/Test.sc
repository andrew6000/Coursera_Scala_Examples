import functional.design.week.two.water.pouring._

object test {

 /*
    We have to define a vector of initial capacity of glasses.
    Let's say, the first glass has capacity four and the second has
    capacity seven.
  */
   val problem = new Pouring(Vector(4,7))

 /*
  And now, what I want to do is I want to say,
      well, what moves are available to me?
*/
  problem.moves
  //= Vector(Empty(0), Empty(1), Fill(0), Fill(1), Pour(0,1), Pour(1,0))

 /*
  Let's put another glass nine in here.
   And then I would have more moves that you can see over here.
  */
 val problem2 = new Pouring(Vector(4,7,9))
  problem2.moves
  //= Vector(Empty(0), Empty(1), Empty(2), Fill(0), Fill(1), Fill(2), Pour(0,1), Pour(0,2), Pour(1,0), Pour(1,2), Pour(2,0), Pour(2,1))
}