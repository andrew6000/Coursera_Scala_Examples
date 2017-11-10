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


    in1 setSignal true
    run()
  /*To run a simulation that change a single of one of the wires,
    so, let's say, let's put a one on, in1 and run the simulation

    What we see is the welcome message simulation has started at time 0
    and at the time 8 new-value of the sum would be true.

    *** simulation started, time = 0 ***
    sum 8 new-value = true

    The value of the carry has not changed so we don't see anything there.
    */




    in2 setSignal true
    run()
    /*
    Placing an input signal on in2 and running the simulation again

    *** simulation started, time = 8 ***
    carry 11 new-value = true
    sum 16 new-value = false


    simulation has restarted at the time 8,
    the last time that we saw a signal change earlier

    And now, we see at time 11, the carry signal would
    get a new value, true.

    And sometimes later at time 16, the sum would be false.

    sum 16 new-value = false
    */

 /* We can now re-track the signal on in1 and run the simulation a third time.

    And that would give us a time 19 that the new carry value is false and
    to sum value is again true at some later time.*/
    in1 setSignal false
    run()
}