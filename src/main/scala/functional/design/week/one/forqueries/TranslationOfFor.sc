

/*
It turns out that there is a systematic translation
scheme that maps for-expressions to certain
combinations of higher order functions.
 So we're going to see in this session that the syntax
 of for-expressions is closely related to three
  higher-order functions, namely map, flatMap, and filter.

  First observation is that all these
  functions can be defined in terms of for.

  So map, flat map and filter can all be expressed as for expressions.

  But in reality it goes the other way. What the Scala compiler will do is
  it will translate four expressions, two combinations of map, flatMap,
  and a lazy variant of filter.
*/

/*
  So for instance, you have a function like map, call it mapFun
  that applies a function f() to every element on the list xs.

  We could write for (x taken from xs) yield f(x).
  That's the same thing that map is usually.
  */
def mapFun[T,U](xs: List[T], f:T => U): List[U] =
      for(x <- xs) yield f(x)

 /*
  FlatMap would be, for x taken from the first list and y taken
  from the result of applying f to each element of the first list,
  yield y. So that's a flap map.
  */
  def flatMap[T,U](xs: List[T], f: T => Iterable[U]) =
      for(x <- xs; y <- f(x)) yield y

/*
  And filter finally can be expressed as a for like this.
  For x taken from xs if the predicate p is true at x, then yield x.
  */
def filter[T](xs:List[T], p:T => Boolean): List[T] =
    for(x <- xs if p(x)) yield x



/*
Let's take our for expression that looks at the pairs of indices whose sum is prime.

So that was our for expression,if we apply our translation scheme mechanically We would be left
with this for-expression here.

So the first one here we have I until n. That's what you see here. It's a generator followed by
a generator so I have a flatMap.

The nested for-expression would take the generator 1 until i, that's a 1 here.

That is followed by a filter, so we have the filter here, with a call to withFilter
and the call to this prime predicate. And finally, the whole thing gets mapped
to a map where we form the paths.

What's noteworthy, is that this is almost exactly the expression we came up with first.
*/
def isPrime(n: Int) = List.range(2, n) forall (x => n % x != 0)
val n = 10

for{
  //generator followed by a generator = flatMap
  i <- 1 until n
  j <- 1 until i
  if isPrime(i+j) //same as call to withFilter.
} yield (i,j)//the whole thing gets mapped to a map, where we form the paths

//Applying a translation scheme to this expression gives the following

(1 until n) flatMap(i =>
  (1 until i).withFilter(j => isPrime(i+j))
    .map(j => (i,j)))//the whole thing gets mapped to a map, where we form the paths
