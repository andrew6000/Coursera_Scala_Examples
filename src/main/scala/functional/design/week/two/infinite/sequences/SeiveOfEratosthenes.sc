



/*
You saw that all elements of the stream except the first one are computed only
when they're needed to produce a result.

One maybe surprising possibility this opens is to define infinite streams.

So, for instance the stream of all integers starting from a given number,
could be define like this.

It would say, def from of an n equals the stream consisting of n followed by from n
+ one.

And normally, of course, this would be a non-terminating recursive computation
but because the stream const operator is lazy in its right operands,
the from n + one here would be actually calculated only if somebody is interested
in the second element of the stream that's defined here.
*/
def from(n: Int): Stream[Int] = n #:: from(n+1)

/*
So, using from, we can now write infinite streams,
for instance, the string of all natural number would be from zero
  */
val nats = from(0)

/*
or the stream of all multiples of four could be
  the stream of all natural numbers, mapped by times four.
  */

val m4s = nats map (_ * 4)
//res0: List[Int] = List(0, 4, 8, 12, 16, 20, 24, 28, 32, 36,

/*
I could then take m4s and take, let's say, first hundred elements,
And convert the whole thing to a list which would force it so there
would indeed see the list that contains the first hundred multiples
of fours.
*/

(m4s take 100).toList

/*
Now, one problem where we can put these techniques to good use is in the
  calculation of prime numbers. There's actually a very ancient algorithm
  called the Sieve of Eratosthenes to calculate these numbers.

  The idea is as follows:
      *Start with all integers from 2, the first prime number.
      *Eliminate all multiples of 2.
      *The first element of the resulting list is 3, a prime number.
      *Eliminate all multiples of 3.
      *Iterate forever. At each step, the first number in the list is a
            prime number and we eliminate all its multiples.
  */

def sieve(s: Stream[Int]): Stream[Int] =
  s.head #:: sieve(s.tail filter (_ % s.head != 0))


/*
So, once we have the sieving method, obtaining all prime numbers is really
  easy. We simply say the prime numbers is sieve of all the integers from two.
  */

val primes = sieve(from(2))

// let's take the first prime numbers and convert it to a list.
primes.take(100).toList


