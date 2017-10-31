package functional.design.week.two.water.pouring


/*
* NOTES ON OPTIMIZATIONS
*
* [In the unoptimized solution]we move blindly.
*
* That means we generate from each possible state, Let's say,
* we have a state here, We generate new states,
* but we will also generate a lot of old states.
*
* So, we do a sort of random walk on the states,
* constantly revisiting also old states.
*
* And these old states don't really bring anything to
* the solution Because when we go back to an old state,
* that's not a path we want to consider because we, by definition,
* there is a shorter path that leads to the same state.
*
* So, the problem we have is that all this exploration
* is rather aimless.
*
* And a better way to do it would be to exclude a state
* that we have visited before.
*
*
*
* */


case class PouringUnoptimized(capacity: Vector[Int]){

  type State = Vector[Int]
  val initialState = capacity map(x => 0)

  trait Move {
    def change(state: State): State
  }

  case class Empty(glass: Int) extends Move {
    def change(state: State) = state updated (glass,0)
  }

  case class Fill(glass: Int) extends Move{
    def change(state: State) = state updated (glass,capacity(glass))
  }

  case class Pour(from: Int, to: Int) extends Move {
    def change(state: State) = {
      val amount = state(from) min (capacity(to) - state(to))
      state updated (from, state(from) - amount) updated (to, state(to) + amount)
    }
  }

  class Path(history: List[Move]) {
    def endState: State = (history foldRight initialState)(_ change _)
    def extend(move: Move) = new Path(move :: history)
    override def toString = (history.reverse mkString " ") + "-->" + endState
  }

  val glasses = 0 until capacity.length

  val moves =
    (for (g <- glasses) yield Empty(g)) ++
      (for (g <- glasses) yield Fill(g)) ++
      (for (from <- glasses; to <- glasses if from != to) yield Pour(from, to))

  val initialPath = new Path(Nil)

  def from(paths: Set[Path]): Stream[Set[Path]] = {
    if (paths.isEmpty) Stream.empty
    else {
      val more = for {
        path <- paths
        next <- moves map path.extend
      } yield next

      paths #:: from(more)
    }
  }

  val pathSets = from(Set(initialPath))

  def solutions(target: Int): Stream[Path] = {
    for {
      pathSet <- pathSets
      path <- pathSet
      if path.endState contains target
    } yield path
  }
}

