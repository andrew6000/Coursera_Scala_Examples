package functional.design.week.three.des.functional.design.week.three.des

/** Class Hierarchy: Simulation > Gates (Wire, AND, OR, NOT) >
 *  Circuits (HalfAdder, FullAdder) > MySimulation and Param trait.
  *
  *  A simple API for discrete event simulation of Digital Circuits
  *
  *  Structure a system into layers, in which the layers are
  *  a domain specific language.
  *
  * The class wire and the functions inverter, AND gate, and OR gate,
  * represent a small description language for digital circuits.
  *
  * Wires transport signals that are transformed by components.

We represent signals using booleans true and false.

The base components (gates) are:
	- The Inverter, whose output is the inverse of its input.
	- The AND Gate, whose output is the conjunction of its inputs.
	- The OR Gate, whose output is the disjunction of its inputs

Other components can be constructed by combining these base
components.

The components have a reaction time (or delay), i.e. their outputs
don’t change immediately after a change to their inputs.
  *
 */

//simulations inside objects that inherit from a trait called Simulation.
trait Simulation {

  /*A discrete event simulator performs 'Actions' which are specified by the user to
  take place at a given moment.

  An 'Action' is simply a function that doesn't take any parameters, and which
  returns unit. So everything in action do, it does by its side side effect.
  And the time when an 'Action' is takes place is simulated. It has nothing to
  do with the actual wall clock time. It is the simulated time.

  */
  //type action that takes an empty parameter list to unit.
  type Action = () => Unit


 /* Then we would have a class of events.

  For convenience, we make it into a case class.

  That way it's easy to match that an event would have a time when
  it should be executed

  and an Action which gives us the function that should be executed.
  */
  case class Event(time: Int, action: Action)


/*The principle idea is to keep in every instance
  of that trait an agenda of actions to perform.

  The agenda would be a list of simulated events, and each event would consist
  of an action and the time when that action should be produced.

  We sort the agenda list in such a way that the actions to be performed first
  are in the beginning, that way we can simply pick them off the front of the
  list to execute them.
  */
  private type Agenda = List[Event] // just a list of events
  private var agenda: Agenda = List()
  // variable agenda of type Agenda, which is initially the empty list.

  /*To handle time, we do that with another private variable, call it curtime,
    that contains the current simulation time, and that one can then be accessed
    with the getter function currentTime.
  */
  private var curtime = 0 //returns the current simulated time in the form of an integer.
  def currentTime: Int = curtime


/*function afterDelay: given delay and a given block

  Can install a block of statements to be performed as an action
  at a time that is delay time units after the current time.*/
  def afterDelay(delay: Int)(block: => Unit): Unit = {
    /*
    creates the event at the given time with the given actions to perform
    */
    val item = Event(currentTime + delay, () => block)
    //inserts it into the agenda
    agenda = insert(agenda, item)
  }

  /*
  Ensures the agenda is time-sorted

  insert an event at the right place in the list that it appears at the
  right position given its time.*/
  private def insert(agenda: List[Event], item: Event): List[Event] = agenda match {

 /* A pattern match on the agenda.

  If the agenda has a first element that has a time that is less or equal to
  the current item's time, then we insert the item in the rest of the list,
  because we should still keep first as the first element.
    */
    case first :: rest if(first.time <= item.time) => first :: insert(rest, item)
    case _ => item :: agenda
  }


  /*function run:
  Lets the user start the simulation and execute all installed
  actions until no further actions remain.*/
  def run(): Unit = {
    afterDelay(0) {
      println("*** simulation started, time = "+currentTime+" ***")
    }
    loop()
  }

  /*
  The event handling loop remove successive elements from the agenda and performs
  the associated actions.

  Agenda is already time sorted and performed the associated actions.
  */
  private def loop(): Unit = agenda match {
    /*
    Performs a pattern match on agenda
    */
    case first :: rest => //agenda is non empty, strip off the first item
      agenda = rest
      curtime = first.time//set the current time to the indicated time of that item
      first.action() //perform the items action
      loop()  //continue with a recursive call to loop
    case Nil => //agenda is empty
      /*
      then the simulation has ended and the function can accept.
      */
  }
}
