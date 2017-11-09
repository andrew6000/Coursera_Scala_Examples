package functional.design.week.four.observer


/*Subscribers have a simple all they need
to have is this handler method, and we pass
the publisher that published new information
as a parameter to that handler.*/
trait Subscriber {

  def handler(pub: Publisher)
}
