package functional.design.week.four.observer


/*Here is a trait for publishers, so it's expected that every publisher
would inherit from that trait to gain the functionality of a publisher.*/
trait Publisher {

/*  What is that functionality? Well, publishers maintain internally
  a set of subscribers which you see here. */
  private var subscribers: Set[Subscriber] = Set()


/*  Initially that set is empty, so you can add a new subscriber by calling
  the subscribe method of a publisher which simply announces a given subscriber.*/
  def subscribe(subscriber: Subscriber): Unit = subscribers += subscriber

 /* The dual of subscribe of course is unsubscribe, so I subscribe I can also
    announce it's no longer interested in published info of that publisher,
  and then the implementation of that would simply remove the subscriber
  from that set.*/
  def unsubscribe(subscriber: Subscriber): Unit = subscribers -= subscriber

/*  And finally, the publisher has a published methods. What that does is it simply goes
  through all subscriber and invokes for each subscriber a handler method that the scrub,
  subscriber must provide with the current publisher it's argument.*/
  def publish(): Unit = subscribers.foreach(_.handler(this))
}
