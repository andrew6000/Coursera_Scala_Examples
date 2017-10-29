

/*
Streams are similar to lists but their tail is evaluated only on demand.

 So, here's how it can define streams. They are, can be built from a constant
 Stream.empty, so the empty value and the stream object, and a constructor Stream.cons.

So to build a stream that consists of the number one and two, you could write Stream.cons one,
Stream.cons two, Stream.empty.
 */

val xs = Stream.cons(1, Stream.cons(2, Stream.empty))


/*
And, of course, streams can also be defined like the other collections by
using the stream object as a factory,

So you could write simply stream of one, two, three.*/

val xs2 = Stream(1,2,3)


/*A third way to produce streams is with a toStream method, which can be applied to
any collection to produce a stream. So in example, you see here, we have a
range one to 1000 and turn it into a stream with a toStream method.*/

val xs3 = (1 to 1000) toStream


/*
It's interesting to see what the result of that call is so what we see here is a
  stream of Int, which is written like this. It's a,
  it's a Stream(1, ?)

What that means is that a stream is essentially a, recursive structurelike a list...
so we have a one here, but the tail is not yet evaluated, so that's why the
interpretor worksheet has printed a question mark.

The tail will be evaluated if somebody wants to know explicitly.
*/
