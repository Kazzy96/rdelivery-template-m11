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

    public void updateInterestRate(double newRate) {
        this.interestRate = newRate;
        System.out.println("Interest rate updated to " + (newRate * 100) + "%");
    }

    @Override
    public String toString() {
        return "[SavingsAccount] " + accountNumber + " | Balance: $" + balance + " | Interest Rate: " + (interestRate * 100) + "%";
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

        savings.deposit(200.0);   // $200 + 5% = $210 added

        savings.updateInterestRate(0.10);  // change rate to 10%

        savings.deposit(200.0);   // $200 + 10% = $220 added
    }
}
