package functional.programming.week.one

import scala.collection.mutable.ListBuffer


object CountChange {

/*  Write a recursive function that counts how many different ways you can make change for an amount, given a list of coin denominations.
  ~ Coursera.org

  The most important sentence to understand this algorithm is “(…)the ways to make change can be divided into two groups: those that
  do not use any of the first kind of coin, and those that do” (from mitpress.mit.edu).

  https://mitpress.mit.edu/sicp/full-text/book/book-Z-H-11.html#%25_idx_722
  */

  def countChange1(amount: Int): Int = {

    def firstDenomination(kindsOfCoins: Int): Int = {
      kindsOfCoins match {
        case 1 => 1
        case 2 => 5
        case 3 => 10
        case 4 => 25
        case 5 => 50
      }
    }

    def cc(amount: Int, kindOfCoins: Int): Int = {
      //If amount is 0 then we just achived our way - the remaining value of our initial amount is 0 so we don’t have to do anything.
      if (amount == 0) 1
      else if (amount < 0 || kindOfCoins == 0){
       /* Although if our amount is smaller than 0 (we subtracted amount with bigger coin than the value of amount) or kindOfCoins
          is 0 (there’s no more coins we can try to use to change money) then previous calculations were wrong and we can’t
        change our money this way.*/
        0 }
      else{

      /*  Here we come to those two groups:

        1.) First addend, cc(amount, kindOfCoins), gives us all ways that do not use any of first kind of coin (because we just deleted it)
        2.) Second addend, cc(amount - firstDenomination(kindOfCoins), kindOfCoins), recursively gives us all ways of counting changes with
        given list and subtracts the amount with value of coin we just used.*/
        cc(amount, kindOfCoins - 1) + cc(amount - firstDenomination(kindOfCoins), kindOfCoins) }
    }

    cc(amount, 5)
  }

  def countChange2(money: Int, coins: List[Int]): Int = (money, coins) match {
    case (0, _) => 1
    case (m, _) if m < 0 => 0
    case (_, cs)  if cs.isEmpty => 0
    case (m, cs) => countChange2(m - cs.head, cs) + countChange2(m, cs.tail)
  }



}
