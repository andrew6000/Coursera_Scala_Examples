

/*
The previous algorithms we've seen for the square root algorithm always mixed
  determination criterion is good enough with the method to calculate new
    approximations.

With streams at our disposal, we can now express the concept of a converging
sequence without having to worry about determination criteria.

So, we could write a stream that represents all the successive approximations of,
of a square root of a given number.

The thing we would do is we would have the usual improved method. And then,
we would say, well, we have a lazy val guesses which is a stream of double,
and the guesses start with one.

And each successive value is obtained by mapping the improved function over
guesses.
  */

def sqrtStream(x:Double): Stream[Double] = {

  def improve(guess: Double) = (guess+x / guess) / 2
  lazy val guesses: Stream[Double] = 1 #:: (guesses map improve)
  guesses
}

/*
So, this might look highly alarming to you,  a little bit like a snake eating it's tail,
   because what happens here is that you apply a map operation on the value that you're
   about to define.

So, won't this blow up in an infinite recursion?

Well, again, no. Because we have the operation stream cons here,
which is lazy in its right operand So, everything actually works out as it should.
  */
sqrtStream(4).take(10).toList

/*So now, we have decoupled the idea of a converging sequence from determination
criterion. Of course, we can add determination
  criterion later. We see that here.
*/

def isGoodEnough(guess: Double, x: Double) =
  math.abs((guess * guess - x) / x) < 0.0001

/*
So, we have to find the is good enough function as before, and we can then
  subject our square root stream to a filter method of all the elements
  that are good enough approximations.
*/

(sqrtStream(4) filter (isGoodEnough(_, 4))).take(10).toList

