package com.rocketFoodDelivery.rocketFood.oop;

public class CheckingAccount extends BankAccount {

    private double overdraftLimit;

    public CheckingAccount(String accountNumber, double balance, double overdraftLimit) {
        super(accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public String toString() {
        return "[CheckingAccount] " + accountNumber + " | Balance: $" + balance + " | Overdraft Limit: $" + overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance + overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrew: $" + amount + " | New balance: $" + balance);
        } else {
            System.out.println("Withdrawal of $" + amount + " denied — exceeds overdraft limit. Balance: $" + balance + " | Overdraft limit: $" + overdraftLimit);
        }
    }

    public static void main(String[] args) {
        CheckingAccount checking = new CheckingAccount("CHK-001", 500.0, 200.0);
        System.out.println("Account: " + checking.getAccountNumber() + " | Opening balance: $" + checking.getBalance() + " | Overdraft limit: $" + checking.getOverdraftLimit());

        checking.withdraw(300.0);  // valid — within balance, no overdraft needed
        checking.withdraw(350.0);  // valid overdraft — balance goes to -$150 (within $200 limit)
        checking.withdraw(100.0);  // rejected — would reach -$250, exceeds $200 overdraft limit
    }
}
