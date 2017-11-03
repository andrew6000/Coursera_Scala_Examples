package functional.design.week.three.des.functional.design.week.three.des.circut.gates

class Wire {

 /*
  A discrete event simulator performs actions which are specified by the user to
  take place at a given moment.

  An action is simply a function that doesn't take any parameters, and which returns unit.
  So everything in action, it does by its side side effect.
  */
  type Action = () => Unit

  /*


   */
  private var sigVal = false  //The value of the current signal from the wire
  private var actions: List[Action] = List()  //The actions to be performed when that signal changes.

  /*
  def getSignal: Boolean = sigVal
  Returns the current value of the signal transported by the wire.

  Current means, at the current simulated time.
  */
  def getSignal: Boolean = sigVal

  /*
  def setSignal(s: Boolean)
  Modifies the value of the signal transported by the wire.

  */
  def setSignal(s: Boolean): Unit = {//The set signal method would take a new signal value.
      if (s != sigVal) {
        sigVal = s
        actions foreach (_())
      }
  }


  /*
   Attaches the specified procedure to the actions of the wire.
   All of the attached actions are executed at each change of the transported signal.

  To be performed every time the signal of a wire changes.

  Here the idea would be when the signal of a wire changes,
  then certain things should happen.

  The things that should happen can be installed so to speak with
  a call to addAction. So, here's an implementation of these ideas for wires.
  * */
  def addAction(a: Action): Unit = {

    actions = a::actions
    a()
  }
}
