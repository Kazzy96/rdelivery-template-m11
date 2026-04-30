package com.rocketFoodDelivery.rocketFood.oop;

import java.util.ArrayList;

public class BankCustomer {

    private String name;
    private ArrayList<BankAccount> accounts;

    public BankCustomer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public double totalBalance() {
        double total = 0;
        for (BankAccount account : accounts) {
            total += account.getBalance();
        }
        return total;
    }

    public static void main(String[] args) {
        BankCustomer customer = new BankCustomer("Alice");

        BankAccount checking = new CheckingAccount("CHK-001", 500.0, 200.0);
        SavingsAccount savings = new SavingsAccount("SAV-001", 1000.0, 0.05);
        BankAccount basic = new BankAccount("ACC-001", 250.0);

        customer.addAccount(checking);
        customer.addAccount(savings);
        customer.addAccount(basic);

        System.out.println("Customer: " + customer.getName());
        System.out.println("  " + checking.getAccountNumber() + " balance: $" + checking.getBalance());
        System.out.println("  " + savings.getAccountNumber() + " balance: $" + savings.getBalance());
        System.out.println("  " + basic.getAccountNumber() + " balance: $" + basic.getBalance());
        System.out.println("Total balance: $" + customer.totalBalance());
    }
}
