

/*
In this session, we cover lazy evaluation.

  Roughly, laziness means do things as late as possible, and never do them twice.

We will apply laziness to streams, and trace how it helps evaluation in a
  concrete stream query.

  The implementation of streams that you've seen in the last session solves the problem
  of avoiding unnecessary computations when the tail value of the stream is not needed,
  but it suffers from another very serious potential performance problem.

 And that is that if tail is called several times the corresponding stream will be
  recomputed each time tail is called. And of course that could by itself cause up to
  exponential blow up in program complexity.

  Fortunately this problem can be avoided by storing the result of the first evaluation
  of tail and reusing the stored result instead of recomputing it the second and third times
  and all other times around.

We can convince ourself, that this optimization is sound, since the pure
  function of language and expression, produces the same result each time it is
  evaluated.

  So instead of re-evaluating the same expression several times, we could just squirrel away the
  first time we have produced the result and reuse that result every time.

That scheme is called lazy evaluation, as opposed to the call by name evaluation
  that we've seen in the last session.

  And also as opposed to the strict evaluation for normal parameters and wild definitions.

  Lazy evaluation is a very powerful principle because it avoids both
unnecessary and repeated computations.

In fact, it's so attractive that a programming language, Haskell, has been built on top of it.
  So Haskell uses lazy evaluation by default, everywhere.
  You could argue, well, why does Scala not do it?

  Well, there's one, or maybe two problems with lazy evaluation which are essentially
  rooted in the fact that lazy evaluation is quite unpredictable in when computations
happen. And how much space they take.

You could argue, in a abstract, pure functional language, it shouldn't really
matter when computations happen, and that's true.

But once you add mutable side effects, which Scala also permits, even though we
haven't used them in this course, you can get into some very confusing situations.

So what Scala does is it uses strict evaluation by default, like the absolute
majority of all programming languages, but it still allows lazy evaluation of value
definitions with the lazy val syntax form.

So if you wrote lazy val x equals expression,

      lazy val x = expr

you would get a lazy evaluation of the value x here.

So what that means is that just in a call by name evaluation that you would get with
def x equals expression.

def x = expr

The expression here would not be evaluated immediately at the point of the finish,
 and it would be delayed, will be delayed until somebody wants the first time the value of x.

But afterwards the behavior between def x and lazy val x diverge.

For def x of course you have the behavior that every time you call x the expression
is reevaluated, whereas for lazy val the expression is reused every time except for
the first one.

*/

/*
So let's test this understanding with an exercise. Consider the following program.

We have a function expr and it defines three values X, Y and Z.

Each definition is preceded by a print statement that prints that the definition is now evaluated.

And then finally we have an expression that makes use of X, Y and Z,
so it does Z plus Y, plus X plus Z, plus Y plus X.
* */

def expr = {

  val x = { print("x"); 1 }
  lazy val y = { print("y"); 2 }
  def z = { print("z"); 3 }
  z + y + x + z + y + x
}

expr
/*

output: xzyz

So let's see how we would approach this problem.

When we evaluate X, probably first have to evaluate the three definitions.

We have a val definition here, the right-hand side gets evaluated immediately
and would print an X.

The lazy val on the def would not be evaluated at the point of definition,
they would be delayed.

Then we would get into the result expression where we first demand the value of Z,
so that would print a Z.

Then we demand the value of y so that would force the lazy valid would print the y.

Then we would demand the value of x

This one is already evaluated, so nothing would be printed.

Then we demand the value of z again, so that would give us another z of y again.

Well, now y is evaluated. So we would just reuse the result we've
evaluated the first time around.

And finally the x is again evaluated.  So the string that gets printed is x z y z.




.*/
