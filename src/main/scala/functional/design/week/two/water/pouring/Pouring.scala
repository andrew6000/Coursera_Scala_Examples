package functional.design.week.two.water.pouring


/*The final program we are going to develop in this course is the solution
to a well-known problem called the water-pouring problem.

The idea is as follows, let's say you are given a faucet and a sink,
And a number of glasses. Right now, I will just draw two of them,
of different sizes.

So, just for the sake of the example, let's assume this glass has size four deciliters,
and this glass has size nine deciliters. What you need to do is put use of a given quantity,
let's say, for the sake of the argument, six deciliters of liquid in one of the glasses,
It doesn't matter which one. However, you're not giving any marking on the glasses.

So, the only knowledge you have is the total capacity of the glasses.

And the moves that are available to you is, you can either fill a glass completely
using the faucet or you can empty it in the sink or you can pour from one glass
to the other, until either the glass from which you pour is empty,
or the glass into which you pour is full. Now, the classical water pouring problem
uses two glasses of the given sizes and the given target size of six deciliters.

But we are going to generalize that a little bit. We want to have an arbitrary number
of glasses of arbitrary given capacities, and an arbitrary target capacity in one
of the glasses. So, let's see how we would model this problem.

First, how do we represent the state of the glasses? Well, the idea would be to represent
a glass as an int ranging from zero to the number of glasses minus one and then our state
would be a vector of int that would give us, for each glass, the number of decilitres
that are in that glass.

And then the question is what kind of moves do we have? Well,
we have three, we can empty a glass, you can fill a glass, or you can pour from a glass
to another glass.

So, let's see if we have, let's say, the two glasses of size four and nine,
and we start in a state where both glasses are empty so that would be state zero and
zero how that would evolve?

So, one thing we can could do is we could fill glass number zero. And that will bring us
into the state four and zero. Or we could fill glass number one. And that would bring us
into the state, zero and nine. From there on, we could pour, starting from this state here,
from glass zero into glass one, that could, would give us the state zero, four, the action
with be pour zero to one.

Or, we could for instance, pour one to zero here. Then, we would end in a state where the
first glass has capacity four its full. And the second glass, has five ounces in it because
that's what remains.

Sometimes, moves lead back to state that we've visited before.
For instance, if we empty the glass one in this state here, Then we would be left with a state four,
Zero, which we can also reach shorter by just a single move here.

Now, you've seen how moves span out. Question is, how do we find now the right solution? How do we
generate moves so that we find the right sequence of moves to lead us to our target capacity,
let's say, that's six here? So now, that we know what moves are, let's see how we can use them to
find the solution to our problem.

The idea would be that we generate all possible move sequences, call them paths, Until we hit on one
that contains one of the glasses with the right target amount of liquid in it.

So, we would then start from an initial state of all glasses empty. And then, generate all possible moves
to new glasses.

Once we have generated all possible moves of length one, we will generate then all possible moves
of length two and so on, Like an onion, Until possibly we hit on a path where we would have one
of the glasses with the target amount of liquid in it, in this case, six.

The idea then, is that we
generate these path sets from inside out, starting with the shortest ones and progressively lengthening
the paths until we hit one which is the right one, or, that's another possibility, until we have
exhausted our search space and there is no solution
*/

// A parameter representing all of the glasses and the the capacity of water they can take
class Pouring(capacity: Vector[Int]) {

  //States -
  type State = Vector[Int]

  //All empty glasses
  val initialState = capacity map (x => 0)

  /*
  Moves
    The idea there is that I would have a glass for each move.
    Let's make it a case glass for convenience.
    And they all would inherit from a common base trait move.

    So, I would have trait move here. And I would then define three case glasses,
     empty. Fill and pour that all extend move.

  */
  trait Move {

    /*
    The next thing to consider is how moves change states.
    There are a number of ways we could record that.

    What I'm going to do here is I'm going to write a method
    change in the move trait that will have to be implemented
    by each case glass. So, change is defined on a move and it takes a state.

    And it gives us a new state. So, that would track how each move changes the state.
    */
    def change(state: State): State
  }
  case class Empty(glass: Int) extends Move {

   /*

   So, for the empty case glass, how is the state changed? Well, we thought the new state would be the old state. With the updated methods, so that, there's a change at one point in the state.
   At what point is it? Well, at my glass, which now will be empty
   So, it's the old state updated at the point of the glass where it's now empty.

   But remember, that updated doesn't destroy the state, the old state will still
   be available, it's just that, a new state object that gets generated by this updated method.

   Updated is a purely functional method here.
    */
   def change(state: State) = state updated (glass,0)
  }
  case class Fill(glass: Int) extends Move{

   /* Here, our change would be that the glass is updated not to be zero, but to be full to its capacity.
      So, glass gets updated to capacity of glass.
    */
   def change(state: State) = state updated (glass,0)
  }
  case class Pour(from: Int, to: Int) extends Move {

    def change(state: State) = {

     /*
     And finally, for pour, There, the problem is a bit more complicated, because it depends whether
     one glass can fully fit into another or not.

     So, what I want to do is, I want to first define the amount that gets poured from one glass to
     the other.

     So, what would that be? Well, It could be that we take everything that is in the from glass in
     the given state, provided that there's enough room in the to glass.

     Or if there's not, then it could also be that we fill the to glass to its capacity.

     So, it would be state of capacity of to minus state of to.

     So, the amount is the smaller of the current filling grade of the glass we pour from and the
     free space in the glass in which, into which we pour.
      */
      val amount = state(from) min (capacity(to) - state(to))
      state updated (from, state(from) - amount) updated (to, state(to)+amount)
    }
  }

/*
  So now, that we know what types of moves are available to us, we still need to generate all
  possible moves, so all possible moves would be empty, an arbitrary glass.

  Fill an arbitrary glass or pour from an arbitrary glass into another. So, let's generate those.


  I have defined one auxiliary state of structure, all glasses that will be arranged,
  that goes from one until capacity length..
*/

  val glasses = 0 until capacity.length

 /* So, what would the possible moves are? Well, one way to do that would be to define it
    as for-expressions.

    So, let's say for g taken from glasses, Yield empty g.

    So, those are the first moves available to me, empty and arbitrary glass. Other moves are for
    g taken from glasses, fill g. And finally, for from, taken from glasses to, Taken from glasses if.

    From is different from to. We can't pour from one glass into the same. Then yield, pour from and to
  */

  val moves =
    (for (g <- glasses) yield Empty(g)) ++
      (for (g <- glasses) yield Fill(g)) ++
      (for (from <- glasses; to <- glasses if from != to) yield Pour(from, to))

}
