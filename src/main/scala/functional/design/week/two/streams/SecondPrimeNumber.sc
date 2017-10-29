import scala.annotation.tailrec

def isPrime(n: Int) = List.range(2, n) forall (x => n % x != 0)


/*For instance, if you wanted to find the
second prime number between 1,000 and 10,000, you could write an expression
strictly according to this specification.

Go from 1000 to 10000,
Filter with this prime predicate, Take the second element.*/
((1000 to 10000) filter isPrime)(1)


/*This is much shorter than the recursive alternative, which you see down here,
where there's a function second prime, which finds the second prime number in a
  given interval between from and to, and that in turn calls a more general function
nthPrime, which takes the nthPrime number in again, an interval between from and to,
and that nthPrime has the usual recursive set up to iterate through the interval
  between from and to.

  Feasible, but definitely much archaic and
  less elegant than the simple expression here.
  */
@tailrec
def nthPrime(from: Int, to:Int, n:Int):Int = {

  if(from >=to) throw new Error("no prime")
  else if(isPrime(from))
    if(n == 1) from else  nthPrime(from+1, to, n-1)
  else nthPrime(from+1, to, n)
}

def secondPrime(from: Int, to:Int) = nthPrime(from,to, 2)
secondPrime(1000,10000)


/*
However, the shorter expression also has a serious problem.

((1000 to 10000) filter isPrime)(1)

It's is evaluation is very, very inefficient, because what we do here is we
construct all the prime numbers between 1000 and 10000, only ever to take its second element.

Presumably there are many more prime numbers between 1000 and 10000.

So you could say, well, Maybe my bound, 10,000, is too high, I should reduce that,
But without knowing a priori where the prime numbers are,
I would always risk that the bound is too low and I would miss finding the prime number.

So we are in the uncomfortable position to be either really bad in performance
  because the bound is too high or risking finding the prime number at all because
the bound is too low.

However, there's a special trick that can make the short code also efficient

The idea is that we want to avoid computing the tail of a sequence until
that tail is needed for the evaluation result, and of course, that might be never.

That idea is implemented in a new class which is called Stream.

  */
