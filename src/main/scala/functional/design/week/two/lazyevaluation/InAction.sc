


def isPrime(n: Int) = List.range(2, n) forall (x => n % x != 0)

def streamRange(lo : Int, hi:Int): Stream[Int] ={
  if(lo>=hi) Stream.empty
  else Stream.cons(lo, streamRange(lo+1,hi))
}

/*

So, all this avoiding of unnecessary computations looks really great,
but maybe you're not yet convinced.

How can we really be sure that our, execution will, in fact, avoid unnecessary
  portions of computations? Well, one way to be sure is to put it to a
test using the substitution model.

Using evaluation with our substitution model.

Let's do that with the expression we started with.
*/

(streamRange(1000, 10000) filter isPrime) apply 1 // res0: Int = 1013

/*
So stream range 1,000, 10,000, filter is prime, apply one.

Let's start reducing that, and see what happens.

So the first thing that happens here is that we have to expand string range.

if(lo>=hi) Stream.empty
        else Stream.cons(lo, streamRange(lo+1,hi)).filter(isPrime).apply(1)

*/

    (if (1000 >= 10000) Stream.empty // by expanding streamRange
    else Stream.cons(1000, streamRange(1000 + 1, 10000))
      .filter(isPrime).apply(1)) //res1: Any = 1013
/*
And here, I've given you the expanded definitions with the actual parameters
replacing the former ones.

The next thing that happens is that the if, then, else is evaluated.
So that would give me the cons expression that we see here.

*/

val C1  =  Stream.cons(1000, streamRange(1000 + 1, 10000)) // C1: Stream.Cons[Int] = Stream(1000, ?)
    //.filter(isPrime).apply(1) //res2: Int = 1013

/*

Let's abbreviate this expression with the cons to C1, so what I would have is C1,
filter is prime, apply, one.

The next thing to do is, we need to expand the filter function.
So, here you see it's definition, and then the rest that needs to be done is apply one on that.

The next thing that happens is that the if, then, else is evaluated.
So that would give me the cons expression that we see here.
*/
   // C1.filter(isPrime).apply(1) //res2: Int = 1013

/*
I have to evaluate the IF then ELSE, so C1 is definitely not empty, because it's a
cons, so I would be left with this ELSE part of the first IF here.
*/

    (if (C1.isEmpty) C1 // by expanding filter
    else if (isPrime(C1.head)) Stream.cons(C1.head, C1.tail.filter(isPrime))
    else C1.tail.filter(isPrime))
    .apply(1) //res3: Int = 1013

/*
And I have to evaluate the head of the C1, the string, that would give me 1,000,
because that's the first parameter past two counts. So I'm left with this expression here.
*/
    (if (isPrime(C1.head)) Stream.cons(C1.head, C1.tail.filter(isPrime))
    else C1.tail.filter(isPrime)) // by eval. if
    .apply(1)

/*
Now the next thing to do is evaluating is prime.

I'll leave that out, because we've done that already, but it's pretty clear that
is prime of 1,000 should return false.

    (if (isPrime(1000)) cons(C1.head, C1.tail.filter(isPrime))
    else C1.tail.filter(isPrime)) // by eval. head
    .apply(1)


So I replace the call by false. I evaluate the if which gives me the, this
expression here.

    (if (false) cons(C1.head, C1.tail.filter(isPrime)) // by eval. isPrime
    else C1.tail.filter(isPrime))
    .apply(1)


And I've evaluated the tail of the C1 constant.

    C1.tail.filter(isPrime).apply(1) // by eval. if


So when I evaluate the tail, that's what I will get. But what I'm left with is the expression
string range of 1,001, 10,000. And then the same thing as filter is prime.

Apply one. In other words, the same expression I started with, only instead of the 1,000,
I have the 1,001 here. And that evaluation sequence continues until I hit the first prime number,
which in this case would be 1,009.


    streamRange(1001, 10000) // by eval. tail
    .filter(isPrime).apply(1)


So this expression would expand by a sequence of reduction steps, to finally
stream range 1,009, 10,000, filter is prime, apply one.

I evaluate stream range again. Is the expression.

Abbreviate  to C2:   cons(1009, streamRange(1009 + 1, 10000))
                      C2.filter(isPrime).apply(1)


And I want to abbreviate that expression to c2.
So I'm left with c2 filters prime apply one.

    cons(1009, C2.tail.filter(isPrime)).apply(1)


I evaluate the filter function on c2, and that gives me an, a sequence of
expressions. Cons 1,009, and then this here, because 1,009 is a prime number,
so it would be included in the result of filter.



So we are left with an expression, like this one here, which is an if then else to ask
whether one equals zero, which is false.

So that would simplify to the second part of the if then else, which you see here.

    if (1 == 0) cons(1009, C2.tail.filter(isPrime)).head // by eval. apply
    else cons(1009, C2.tail.filter(isPrime)).tail.apply(0)


Now, what we need to do is we need to evaluate tail.

    cons(1009, C2.tail.filter(isPrime)).tail.apply(0)


That would in turn, force the express tail part of these, this console.
So we would get C2 tail at filter is prime, apply zero.

    C2.tail.filter(isPrime).apply(0)


The next thing to calculate, again, is the tail over here.
So that now would give us the next stream range.
The Tail part of c2. Again, filter is prime apply zero.


So what we see is we again, left with essentially, the expression we started
with, only now we have. 1,010 here and we have zero here.
Where we started with 1,000 on the left and one on the right.

    streamRange(1010, 10000).filter(isPrime).apply(0)


So that process would continue until we hit the second prime number, 1,013.
And now the computation is about to wrap up.

    streamRange(1013, 10000).filter(isPrime).apply(0)


So the stream range function would expand as usual.

    cons(1013, streamRange(1013 + 1, 10000)) // by eval. streamRange
    .filter(isPrime).apply(0)


We make it a shorthand. Call it C3, for this expression.
So we, we have C3 filter is prime, apply zero.

    C3.filter(isPrime).apply(0)

We apply the filter function that would say, well, 1013 is a prime number so let's
include it in the list. Have the tile expression here.  Apply zero of that.


cons(1013, C3.tail.filter(isPrime)).apply(0)


We apply, evaulate the apply function and that would pull out the first element 1013.

    cons(1013, C3.tail.filter(isPrime)).apply(0)


And that's the result of the computation.

    1013


That was quite tedious to follow that far, but imagine how more tedious it would have
been if we had to evaluate actually all the prime numbers between 1,000 and 10,000.

Here you could convince yourself that indeed, we never look beyond 1,013,
all the other prime numbers remain undiscovered and unevaluated in this

*/


