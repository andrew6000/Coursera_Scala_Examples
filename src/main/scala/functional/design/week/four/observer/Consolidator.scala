package functional.design.week.four.observer


/*Observes a list of bank accounts and that
would always be up to date with the total
balance of all the bank accounts.*/
class Consolidator(observed: List[BankAccount])
                                extends Subscriber {
  /*Consolidator is a subscriber. subscribes itself to
  all observed bank accounts as an initialization
  action observed for each subscribe*/
  private var total: Int = sum()
  private def sum() =
  observed.map(_.currentBalance).sum
  def handler(pub: Publisher) = sum()
  def totalBalance = total
}
