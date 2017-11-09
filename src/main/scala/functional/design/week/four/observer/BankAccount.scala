package functional.design.week.four.observer

class BankAccount extends Publisher {
  private var balance = 0
  //no way to acces other than accessor
  def currentBalance: Int = balance // <---  Provides the current state of the variable
  def deposit(amount: Int): Unit =
    if (amount > 0) {
      balance = balance + amount
      publish() // <--- Every time we change the state of the bank account
    }
  def withdraw(amount: Int): Unit =
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      publish() // <--- Every time we change the state of the bank account
    } else throw new Error("insufficient funds")
}