
/*
Here it turns out that the classical for loop that you'll find
in C or C++ or Java, cannot be modeled simply by a higher order function.

So if you look at such a for loop, you see the example here, you see
that there's actually a definition here, that i equals one, that
introduces a variable that is used later on in the other parts
of the for loop.

And that's something that cannot be straightforwardly achieved by using
just higher order function applications. And, as a matter of fact, Scala
does not have this kind of for loop.

What it has is a kind of for loop that is similar to Java's extended for loop,
so you would write something that's equivalent to the example up here like this,
for i ranging over one until three, system out.print i plus space.

And that would display one and two. Now, this looks pretty much like a for expression,
like the ones you've seen in the functional programming course and also in the first
week of this course, and in fact that's no accident.
*/

// Scala does not have this kind of for loop.
//for(int i=1; i < 3; i=i+1) { System.out.print(i + " ); }


for(i <- 1 until 3) { System.out.print(i + " ") }






/*
For loops translate quite similarly to for expressions,
but where for expressions translate into combinations of
functions, map and flatMaps, for loops translate into
combinations of the function foreach.

So, foreach is a function that's defined on all collections
with elements of type T.

It would take an argument which is a function from T to Unit,
give you back a Unit. And its effect would be to apply the
given function argument f to each element of the collection.

So, here's an example where you use two nested generators.

    for(i <- 1 until 3; j <- "abc") println(i+" "+j)

You let i range from one until three, j range over the elements
of the string abc, and you print i plus space plus j.

So in this case, that should print 1a, 1b, 1c, 2a, 2b, 2c.

That expression gets expanded into two nested calls of foreach.

    (1 until 3) foreach (i => "abc" foreach (j => println(i+" "+j)))

You start with the first range, one one until three.

You apply the closure, i arrow something, to that range.

That closure takes each element i, taken from one until three,
and there's a nested call of foreach, where the base
collection is the string abc.

The loop variable is the j, and the final result is the println.

*/

for(i <- 1 until 3; j <- "abc") println(i+" "+j)
// Translates to
(1 until 3) foreach (i => "abc" foreach (j => println(i+" "+j)))

object loop {
  def power(x: Double, exp: Int): Double = {
    var r = 1.0
    var i = exp
    //the condition must be passed by name so it can be reiterated every time
    while (i>0) {
      r = r*x
      i = i-1
    }
    r
  }                                               //> power: (x: Double, exp: Int)Double

  /** A regular While loop */
  def WHILE(condition: => Boolean)(command: => Unit): Unit = {
    if(condition) {
      command
      WHILE(condition)(command)
    }
    else ()
  }                                               //> WHILE: (condition: => Boolean)(command: => Unit)Unit
  var cnt:Int = 0                                     //> cnt  : Int = 0
  WHILE(cnt>0)(cnt-=1)
  cnt                                             //> res0: Int = 0

  /** A do while loop */
  def REPEAT(command: => Unit)(condition: => Boolean): Unit = {
    command
    if(condition) () //return
    else REPEAT(command)(condition)
  }                                               //> REPEAT: (command: => Unit)(condition: => Boolean)Unit



  REPEAT(cnt-=1)(cnt>=4)
  cnt                                       //> res1: Int = 4

  /** DO { command } UNTIL (condition) loop */
  class DO(command: => Unit) {
    def WHILE(condition: => Boolean): Unit = {
      command
      if (condition) ()
      else DO {command} WHILE (condition)
    }
  }
  object DO {
    def apply(command: => Unit) = new DO(command)
  }

  cnt = 0
  DO {
    cnt = cnt + 1
  } WHILE (cnt >= 4)
  cnt                                       //> res2: Int = 4

  /** For loop */
  //like Scala's for-expressions and foreach on the scala collection Range
  for(i <- 1 until 3) { println(i + " " ) } //> 1
  //| 2
  (1 until 3) foreach (i => println(i + " " ))    //> 1
  //| 2

  for(i <- 1 until 3; j <- "abc") println(i + " " + j)
  //> 1 a
  //| 1 b
  //| 1 c
  //| 2 a
  //| 2 b
  //| 2 c
  (1 until 3) foreach (i => "abc" foreach (j => println(i + " " + j)))
  //> 1 a
  //| 1 b
  //| 1 c
  //| 2 a
  //| 2 b
  //| 2 c



}