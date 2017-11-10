package functional.design.week.three.des.functional.design.week.three.des.circut.gates

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


abstract class Gates extends Simulation {
  //abstract methods - units of simulated time.
  def InverterDelay: Int
  def AndGateDelay: Int
  def OrGateDelay: Int
  
  class Wire {

    //the state of a wire is modeled by two variables, 'sigVal' and 'actions'

    //represents the current val of the current signal from the wire
    private var sigVal = false 
    
    //represents the actions attached to the current signal
    //and to be performed when that signal changes
    private var actions: List[Action] = List() 
    
    //returns the current value of signal transported by the wire
    //'current' means, at the current simulated time.
    def getSignal: Boolean = sigVal

    //modifies the value of the signal that's transported by the wire.
    def setSignal(s: Boolean): Unit = {
      if(s!=sigVal) {
        sigVal = s
        actions foreach (_()) //same as for (a <- actions) a()
      }
    }
    
   /* Attaches the specific procedures to the actions of the wire.
      Performed every time the signal of a wire changes

     Here the idea would be when the signal of a wire changes,
     then certain things should happen.

     The things that should happen can be installed so to speak
     with a call to add action.
    */
    def addAction(a: Action): Unit = {
      actions = a :: actions
      a()
      /*What it also does it turns out to be technically necessary to get the
        simulation off the ground is once we add an action we immediately
        perform it a first time because otherwise it turns out that the
        simulation would essentially rest in an inert state forever.

        So we have to perform the action the first time to get things
        off the ground.
        */
    }
  }  
  
  //============== The Logic Gates ============================

  /*
  The inverter is implemented by installing an action on its input wire.

  An Inverter action should be performed every time the input wire changes
  it's signal.

  Add this invert action to the input wire, so that way the wire itself
  would perform this action every time it's signal changes.

  That way, that action would be performed each time the input write changes.
    And what would the action do? Well, it would produce the inverse of the
  input signal, but not immediately but only after a certain delay.*/

  //takes an input wire and an output wire
  def inverter(input: Wire, output: Wire): Unit = {

    def inverterAction(): Unit = {

     /* So, what to invert?  the input wire

      take the signal of the input wire and then we would set the
      output to the negation of the input signal, but we would do
      that only after a certain delay.*/
      val inputSig = input.getSignal
      afterDelay(InverterDelay) { output setSignal !inputSig }
      /*After delay it says, perform this block here, invert the delay time units after the
      current time.*/
    }

    input addAction inverterAction
  }


 /* The action of the AND gate  would produce the AND, the conjunction of the input
    signals of the output wire.

  It would happen after a certain delay, AndGateDelay. So that gives us the following
    implementation here:

    AND gate takes two inputs and one output.*/

  def andGate(in1: Wire, in2: Wire, output: Wire): Unit = {
    def andAction(): Unit = {

      //take the values of the two input signal
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal

      /*Performed whenever one of the two signal changes.
      That way we're sure that whenever one of the two input changes,
      the output signal would be recomputed.*/
        afterDelay(AndGateDelay) { output setSignal (in1Sig & in2Sig) }

      /*After AndGateDelay, we set the output to the logical AND of the two values
      of the input signals.*/
    }
    in1 addAction andAction
    in2 addAction andAction
  }

  /*Implemented quite analogously to the AND gate

    To go to the OR gate we simply change the action
    in orAction from the logical conjunction to
    logical disjunction.

     For orGate, we compute the two signals in1Sig and in2Sig before writing them in value definitions

 We would sample first, then wait orGateDelay time units and then set the output afterwards.

 It does make a big difference whether the signals are computed outside after delay, or inside.*/
  def orGate(in1: Wire, in2: Wire, output: Wire): Unit = {
    def orAction(): Unit = {
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal
      //perform whenever one of the two signal changes
      afterDelay(OrGateDelay) { output setSignal (in1Sig | in2Sig) }//logical disjunction
    }
    in1 addAction orAction
    in2 addAction orAction
  }

  /*'orGate2' does not model OR gates faithfully.
  *
  * We compute the two signals in1Sig and in2Sig inline, inside the afterDelay call,
  * instead of before writing them in value definitions.
  *
  * But, the sampling would take place at the time after OR gate delay, and the output
  * change in the sampling would appear at exactly the same time.
  *
  * it does make a big difference whether the signals are computed outside after delay,
  * or inside and consequently orGate2 does not model OR Gates faithfully.
  * */
  def orGate2(in1: Wire, in2: Wire, output: Wire): Unit = {
    def orAction(): Unit = {
      afterDelay(OrGateDelay) {
        output setSignal (in1.getSignal | in2.getSignal) }
    }
    in1 addAction orAction
    in2 addAction orAction
  }

  
  /* In this alternative orGate, the time to stabilize event is longer.
   *
   * If we replaced our implementation of orGate by the alternative orGate
   * that you've just seen, what would happen?
   *
   * What you compare to the earlier simulation would you see a change?
   *
   * Initially we get some glitches on the sum value. There's some different behavior here.
   *
   * The times are different, and orGateAlt may also produce additional events.
   *
    * */
  //a | b == !(!a & !b)
  def orGateAlt(in1: Wire, in2: Wire, output: Wire): Unit = {
    val notIn1, notIn2, notOut = new Wire
    inverter(in1, notIn1)
    inverter(in2, notIn2)
    andGate(notIn1, notIn2, notOut)
    inverter(notOut, output)
  }
  
  /*
  function probe

  A way to examine the changes of the signals on the wires.  Attached to a wire

  Is something that you can attach to a wire. Sort of like an oscillator that
  tracks a digital signal.

  Takes the form of a component, much like And gate or all the other components
  in the system.

  name parameter is typically the name of the wire.

  The action parameter consists of printing the name of the wire, the current time, and
  the new signal on that wire. And what it would do in installation is
it would add this action to the wire.

  */
  def probe(name: String, wire: Wire): Unit = {
    def probeAction(): Unit = {
      println(s"$name $currentTime new-value = ${wire.getSignal}")
    }
    wire addAction probeAction
  }
}


abstract class Circuits extends Gates {
  //Half Adder, that takes two inputs and converts them into a sum in the carry.
  def halfAdder(a: Wire, b: Wire, s: Wire, c: Wire): Unit = {
    val d, e = new Wire  // Internal Wires
    orGate(a, b, d)
    andGate(a, b, c)
    inverter(c, e)
    andGate(d, e, s)
  }
  
  //A full bit adder
  def fullAdder(a: Wire, b: Wire, cin: Wire, sum: Wire, cout: Wire): Unit = {
    val s, c1, c2 = new Wire
    halfAdder(b, cin, s, c1)
    halfAdder(a, s, sum, c2)
    orGate(c1, c2, cout)
  }
}

  /*
  A trait Parameters with the delays, and then your actual simulation object would
  extend the circuits class with Parameters.

  Fixes the technology specific constants*/
trait Parameters {
  def InverterDelay = 2
  def AndGateDelay = 3
  def OrGateDelay = 5
}


/*
* SUMMARY
*
* It's seen that adding state and assignments makes our mental model of computation
* more complicated.
*
* In particular, we lose the property of referential transparency,
* which says that it doesn't matter whether we use a name or the thing it refers to.
*
* The other thing that we lose is the substitution model so we do not have any more
* an easy way to trace computations by rewriting.
*
* On the other hand, assignments allow us to formulate some programs in an
* elegant and concise way.
*
* We've seen that with the example of discreet event simulation where a system is
* represented by a list of action and that list was a neutral variable, it changed
* during the time of simulations.
*
* The effect of the actions when they're called would, in turn, change the state of objects.
* And they could also install other actions to be executed in the future.
*
* You've seen that in this way, combining higher functions and assignments in state led to some very,
  very powerful techniques that let you express fundamentally complex computations in a concise and understandable way.

  In the end, it's a trade-off. You get more expressiveness that helps you tackle certain problems in a simpler way.

  But on the other hand, you lose tools for reasoning about your program preferential transparency into substitution model.

  So, I guess the moral would be that you should stick to the purely function model whenever you can.

  And you should use state responsibly when you must.
*
*
* */