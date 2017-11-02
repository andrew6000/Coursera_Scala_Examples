


object ForQuery {

  case class Book( title: String, authors: String*)

  val books: List[Book] = List(
    Book("Structure and Interpretation of Computer Programs", "Abelson, Harold", "Sussman, Gerald J."),
    Book("Principles of Compiler Design", "Aho, Alfred", "Ullman, Jeffrey"),
    Book("Programming in Modula-2", "Wirth, Niklaus"),
    Book("Elements of ML Programming", "Ullman, Jeffrey"),
    Book("Elements of ML Programming 2", "Ullman, Jeffrey"),
    Book("The Java Language Specification", "Gosling, James", "Joy, Bill", "Steele, Guy", "Bracha, Gilad")
  )

  val titles1 = for(b <- books; a <- b.authors if a startsWith "Aho," ) yield b.title
  val titles2 = for(b <- books if b.title.indexOf("Program") >= 0 ) yield b.title

/*
    val authorWith2Books = ???

    We want to find the names of all authors who have written at least two books
    that are present in the database.

    So a way to do this would be to actually be have two iterators ranging over
    the books by database.

    So we let b 1 range of our books and b2 range of our books and
    we demand that b 1 and b 2 are different.

    Now we have pairs of different books. And we let a1 and a2 range over
    the authors of these pairs.

    And if we find a match, so if you find an author that appears in the
    authors list of both b1 and b2 then we find an author that has published
    at least two books so we give it back.

    If we do that, then I have put the query that you see on the slide up here.
    */
  val authorWith2Books =  for {
    b1 <- books
    b2 <- books
    if(b1 != b2) // For pairs of different books
    a1 <- b1.authors
    a2 <- b2.authors
    if(a1 == a2) // Author with two books published
  }yield a1

  /*Let's see what the result is.

  List(Ullman, Jeffrey, Ullman, Jeffrey)

  While we get Jeffrey Ullman, that's fine but, we actually get him twice.

  The reason is that we have two generators that both go over books so,
  each pair of book will show up twice once with the argument swapped.

  How can we avoid this? Well one easy way to avoid that would
  be to say instead of just demanding that the two books are different,
  we demand that the title of the first must lexicographically smaller
  than the title of the second book.

  So, that would mean that in our previous
  one, we would get the two books as before but we wouldn't
  get the pair in reversed orders, because in lexicographical order,
  one book comes before the other.

  And what we get array is indeed just a single author.

  List(Ullman, Jeffrey)

  */

  val authorWith2Books2 =  {for {
      b1 <- books
      b2 <- books
      // Title of the first must lexicographically smaller than the title of the second book.
      if(b1.title < b2.title)
      a1 <- b1.authors
      a2 <- b2.authors
      if(a1 == a2) // Author with two books published
    }yield a1
  }
    .distinct //remove duplicate authors
}

/*
  But are we done yet? A question for you. What happens if an author
  has published three books?

  List(Ullman, Jeffrey, Ullman, Jeffrey, Ullman, Jeffrey)

  Well, we see that the same author is printed three times.

  Why is that? Obviously the problem now is even with this added condition,
  we have three possible pairs of books.

  So if you have a book, B1, B2, B3, all published by the same author,
  then you have three possible pairs of two books out of these threes,
  and for each of the three possibilities the same author will be printed.

  So the author is printed three times.

  What can we do about this, how can we avoid printing the author several times
  by one solution would be to remove duplicate authors when result is twice or
  several times.

  There's a function for this it's called distinct it works on all sequences.


  */



