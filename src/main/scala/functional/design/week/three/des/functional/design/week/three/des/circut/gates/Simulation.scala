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
  type Action = () => Unit
  
  case class Event(time: Int, action: Action)
  
  private type Agenda = List[Event]
  private var agenda: Agenda = List()
  
  //to handle time
  private var curtime = 0 //returns the current simulated time in the form of an integer.
  def currentTime: Int = curtime


/*function afterDelay:
  Can install a block of statements to be performed as an action
  at a time that is delay time units after the current time.*/
  def afterDelay(delay: Int)(block: => Unit): Unit = {   
    val item = Event(currentTime + delay, () => block)
    agenda = insert(agenda, item)
  }
  
  //Ensures the agenda is time-sorted
  private def insert(agenda: List[Event], item: Event): List[Event] = agenda match {
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
  
  //The event handling loop remove successive elements from the agenda and performs
  //the associated actions
  private def loop(): Unit = agenda match {
    case first :: rest => 
      agenda = rest
      curtime = first.time
      first.action()
      loop()
    case Nil => 
  }
}


abstract class Gates extends Simulation {
  //abstract methods
  def InverterDelay: Int
  def AndGateDelay: Int
  def OrGateDelay: Int
  
  class Wire {
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
        simulation would essentially rest in an inert state forever.*/
    }
  }  
  
  //The Logic Gates
  def inverter(input: Wire, output: Wire): Unit = {
    def inverterAction(): Unit = {
      val inputSig = input.getSignal
      afterDelay(InverterDelay) { output setSignal !inputSig }
    }
    input addAction inverterAction
  }
  
  def andGate(in1: Wire, in2: Wire, output: Wire): Unit = {
    def andAction(): Unit = {
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal
      //perform whenever one of the two signal changes
      afterDelay(AndGateDelay) { output setSignal (in1Sig & in2Sig) }
    }
    in1 addAction andAction
    in2 addAction andAction
  }
  
  def orGate(in1: Wire, in2: Wire, output: Wire): Unit = {
    def orAction(): Unit = {
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal
      //perform whenever one of the two signal changes
      afterDelay(OrGateDelay) { output setSignal (in1Sig | in2Sig) }
    }
    in1 addAction orAction
    in2 addAction orAction
  }
  
  /* In this alternative orGate, the time to stabilize event is longer */
  //a | b == !(!a & !b)
  def orGateAlt(in1: Wire, in2: Wire, output: Wire): Unit = {
    val notIn1, notIn2, notOut = new Wire
    inverter(in1, notIn1)
    inverter(in2, notIn2)
    andGate(notIn1, notIn2, notOut)
    inverter(notOut, output)
  }
  
  /* Probe is attached to a wire */
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

//fixes the technology specific constants
trait Parameters {
  def InverterDelay = 2
  def AndGateDelay = 3
  def OrGateDelay = 5
}
