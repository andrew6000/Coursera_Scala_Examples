package functional.design.week.four.observer

trait Subscriber {

  def handler(pub: Publisher)
}
