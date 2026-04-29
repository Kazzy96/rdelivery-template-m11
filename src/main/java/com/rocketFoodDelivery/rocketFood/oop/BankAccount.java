package com.rocketFoodDelivery.rocketFood.oop;

public class BankAccount {

    protected String accountNumber;
    protected double balance;

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: $" + amount + " | New balance: $" + balance);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: $" + amount + " | New balance: $" + balance);
        } else {
            System.out.println("Withdrawal of $" + amount + " denied — insufficient funds. Balance: $" + balance);
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount("ACC-001", 500.0);
        System.out.println("Account: " + account.getAccountNumber() + " | Opening balance: $" + account.getBalance());

        account.deposit(200.0);       // valid deposit
        account.withdraw(100.0);      // valid withdrawal
        account.withdraw(700.0);      // rejected — exceeds balance
        account.deposit(50.0);        // another deposit
        account.withdraw(650.0);      // valid — exactly empties the account
    }
}
