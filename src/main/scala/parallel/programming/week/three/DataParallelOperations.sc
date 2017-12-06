

def play(a: String, b: String): String = List(a, b).sorted match {
  case List(x, "scissors") => {
    if(x == "rock") "rock"
    else "scissors"
  }
  case List("paper", "rock") => "paper"
  case List(a, b) if a == b => a
  case "" ::  y => b

}

Array("paper", "rock", "pattern", "scissors").par.fold("")(play)
//> res9: String = scissors
Array("paper", "rock", "pattern", "scissors").par.fold("")(play)
//> res10: String = scissors
Array("paper", "rock", "pattern", "scissors").par.fold("")(play)
//> res11: String = scissors
Array("paper", "rock", "pattern", "scissors").par.fold("")(play)
//> res12: String = scissors


def isVowel(c: Char): Boolean =
  List('a','e','i','o','u') exists (a => a == Character.toLowerCase(c))
//> isVowel: isVowel[](val c: Char) => Boolean

/* The following program does not compile
 * because the z is 0 and not a Char
 */
/*
Array('E', 'P', 'F', 'L').par.fold(0)((count, c) =>
  if(isVowel(c)) count+1 else count)
*/

//However, foldLeft compiles because for foldLeft, z element does not have to
//be the same type as the elements in the array

Array('E', 'P', 'F', 'L').foldLeft(0)((count, c) =>
  if(isVowel(c)) count+1 else count)        //> res13: Int = 1


val helloVowels = "hellOWorldA".toList map (c => isVowel(c))


/** The aggregate Operation - more powerful than fold
  * def aggregate[B](z: B)(f: (B,A) => B, g: (B, B) => B): B
  * how it works is it divides the data into many smaller parts for mutiple
  * processors to compute via foldLeft. Then it combines back into a
  * form similar to fold
  */
/* Use aggregate to count the number of vowels in an array */

val aggrVowels = Array('E', 'P', 'F', 'L').par.aggregate(0)(
  (count, c) => if(isVowel(c)) count + 1 else count,
  _ + _
)                                               //> res15: Int = 1