package functional.design.week.three.des


/*



*/

trait Simulation {

  /*
    And the time when an action is takes place is simulated.
    It has nothing to do with the actual wall clock time.
    It is the simulated time. So, concretely, we are going to write
  simulations inside objects that inherit from a trait called simulation.

  And that trait has the following signature. There's a function currentTime,
  which returns the current simulated time in the form of an integer.
   */
  def currentTime: Int = ???

  /*
  Then there's a function afterDelay, where the user gives can install a
  block of statements to be performed as an action.

    At a time that is delay time units after the current time.
    */
  def afterDelay(delay: Int)(block: => Unit): Unit = ???

 /*
  And finally, there's the function run, which lets the user start the
    simulation and execute all installed actions until no further
  actions remain.*/
  def run(): Unit = ???

}
