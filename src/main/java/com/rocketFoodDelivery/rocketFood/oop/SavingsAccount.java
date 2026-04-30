package com.rocketFoodDelivery.rocketFood.oop;

public class SavingsAccount extends BankAccount {

    private double interestRate;

    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void deposit(double amount) {
        double interest = amount * interestRate;
        double effectiveDeposit = amount + interest;
        balance += effectiveDeposit;
        System.out.println("Deposited: $" + amount + " | Interest earned: $" + interest + " | New balance: $" + balance);
    }

    public static void main(String[] args) {
        SavingsAccount savings = new SavingsAccount("SAV-001", 1000.0, 0.05);
        System.out.println("Account: " + savings.getAccountNumber() + " | Opening balance: $" + savings.getBalance() + " | Interest rate: " + (savings.getInterestRate() * 100) + "%");

        savings.deposit(200.0);   // $200 + 5% interest = $210 added
        savings.deposit(500.0);   // $500 + 5% interest = $525 added
        savings.withdraw(100.0);  // inherited withdraw — no interest on withdrawals
    }
}
