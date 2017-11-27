package functional.programming.week.one

import scala.annotation.tailrec

object BalanceParentheses {

 def balance1(chars: Array[Char]): Boolean ={

   @tailrec
   def balance(chars: Array[Char], count:Int = 0): Boolean = (chars, count) match {
     case (cs, 0)  if cs.isEmpty => true
     case (cs, _)  if cs.isEmpty => false
     case (cs, c)  => cs.head match {
       case '(' => balance(cs.tail, c+1)
       case ')' if c > 0 => balance(cs.tail, c-1)
       case ')' => false
       case _  => balance(cs.tail, c)
     }
   }

   balance(chars,0)
 }



}
