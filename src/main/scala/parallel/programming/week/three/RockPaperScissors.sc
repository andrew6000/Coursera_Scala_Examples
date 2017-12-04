

/*
   * rock paper scissors
   */

def play(a: String, b: String): String = List(a, b).sorted match {
  case List(x, "scissors") => {
    if(x == "rock") "rock"
    else "scissors"
  }
  case List("paper", "rock") => "paper"
  case List(a, b) if a == b => a
  case "" ::  y => b

}                                               //> play: (a: String, b: String)String

//The data parallel schedule is allowed to organize the reduction tree differently:
//play(play("play", "rock"), play("paper", "scissors")) == "scissors"
//play("play", play("rock", play("paper", "scissors"))) == "paper"

Array("paper", "rock", "pattern", "scissors").par.fold("")(play)
//> res9: String = scissors
Array("paper", "rock", "pattern", "scissors").par.fold("")(play)
//> res10: String = scissors
Array("paper", "rock", "pattern", "scissors").par.fold("")(play)
//> res11: String = scissors
Array("paper", "rock", "pattern", "scissors").par.fold("")(play)
//> res12: String = scissors