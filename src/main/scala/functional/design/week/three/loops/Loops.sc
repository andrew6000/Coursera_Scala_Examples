
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