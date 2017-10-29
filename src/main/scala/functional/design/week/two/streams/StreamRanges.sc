

/*
Let's look at stream evaluation in a little bit more detail using ranges as an example.

Instead of the usual range and then toStream expression, (1 to 1000) toStream

I have decided that I wanted to work from first principles
                    and I wrote a streamRange function directly here.

So that's the usual recursive function, if the lower bound is greater or equal to
higher bound, I return the empty stream,

Otherwise, it's a cons of the lower bound and a recursive call of streamRange(lo + one, Hi)).
*/

def streamRange(lo : Int, hi:Int): Stream[Int] =
   if(lo>=hi) Stream.empty
   else Stream.cons(lo, streamRange(lo+1,hi))

/*
If you compare that to the function that does the same thing for lists, here is
  listRange and turns out that the two functions are actually completely isomorphic.

They have exactly the same structure,
Only one returns a stream, the other returns a list,

And the empty stream here corresponds to the Nil case for the lists and the cons
case for the streams corresponds to the cons operator for the lists.

*/

def listRange(lo : Int, hi:Int): List[Int] =
  if(lo>=hi) Nil
  else lo :: listRange(lo+1,hi)

val stream = streamRange(1,10)
val list = listRange(1,10)

/*

Yet their operational behavior is completely different........

If we look at listRaange first, what would that function do if we have listRange
(one, ten). What would the thing do?

Well, it would create a list with the one here and so on,
Until I have the ten here and I have a Nil,So it would generate the complete list in one go.

list: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9)

Whereas for a streamRange, what would happen is it would create a first cons
  pair with a one here and then the rest would be a ?

stream: Stream[Int] = Stream(1, ?)

 So it wouldn't be generated, instead, there would be an object that would know
 how to reconstitute the stream if somebody demands it
  */



def streamRange2(lo : Int, hi:Int): Stream[Int] ={
  print("$lo ")
  if(lo>=hi) Stream.empty
  else Stream.cons(lo, streamRange(lo+1,hi))
}

val strem2List = streamRange2(1,10).take(3).toList

/*
What would you expect gets printed? Nothing or one of these results here?

So let's see how we would reason about it.

Have you seen when we take the streamRange(1, 10),
we just evaluate the first element here and the rest is, is yet unknown.

The take method on Streams, if we look at its definition and evaluate it,
then it would do nothing special, it would essentially again return as a stream
where the only node the first element.

But then if we finally convert the stream to a list, then of course, we need to
force it because our list can't be left unevaluated.

So by the time we do that, we create a list with three elements one, two, three
and the rest is Nil, That's the result.

 And to produce that list, we have to go down three elements in the orig, original
argument stream in the streamRange.

$lo strem2List: List[Int] = List(1, 2, 3)
*/
