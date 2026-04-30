package com.rocketFoodDelivery.rocketFood.oop;

import java.util.ArrayList;

public class BankAccount {

    protected String accountNumber;
    protected double balance;
    protected ArrayList<String> transactions;

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    protected void recordTransaction(String transaction) {
        transactions.add(transaction);
    }

    public ArrayList<String> getTransactionHistory() {
        return transactions;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: $" + amount + " | New balance: $" + balance);
        recordTransaction("Deposit: +$" + amount + " | Balance: $" + balance);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: $" + amount + " | New balance: $" + balance);
            recordTransaction("Withdrawal: -$" + amount + " | Balance: $" + balance);
        } else {
            System.out.println("Withdrawal of $" + amount + " denied — insufficient funds. Balance: $" + balance);
            recordTransaction("Withdrawal denied: $" + amount + " — insufficient funds. Balance: $" + balance);
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

        account.deposit(200.0);
        account.withdraw(100.0);
        account.withdraw(700.0);
        account.deposit(50.0);
        account.withdraw(650.0);

        System.out.println("\n--- Transaction History ---");
        for (String t : account.getTransactionHistory()) {
            System.out.println(t);
        }
    }
}
