import functional.design.week.three.des.functional.design.week.three.des.circut.gates.{Circuits, Parameters}

object test {

  //Extend the circuits that I have defined so far and would mix in the parameters.
  object sim extends Circuits with Parameters
  //import everything in that object for convenience
  import sim._

  //get started by creating some wires for a halfAdder
  //output wires would be in1 and in2
  //output wires would be sum and carry
  val in1, in2, sum, carry = new Wire

  //We next connect these wires with a halfAdder
  halfAdder(in1, in2, sum, carry)



  halfAdder(in1, in2, sum, carry)
  //to see something we put probes on the two output wires,
  // so we would have our probes on the sum wire
  //gives us immediately the values of the wires.
  probe("sum", sum)
  probe("carry", carry)

  /*
    sum 0 new-value = false

    carry 0 new-value = false

    Sum and carry would both be false.

    That's a side effect of immediately executing an action
    once we install it and the action of the probe is simply to
    print the current value of the wire.*/


  /*To run a simulation that change a single of one of the wires, so,
    let's say, let's put a one on, in1 and run the simulation*/
    in1 setSignal true
    run()




    in2 setSignal true
    run()




    in1 setSignal false
    run()
}