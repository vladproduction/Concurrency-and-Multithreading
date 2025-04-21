package com.vladproduction.ch03_thread_safety.threading_issues.race_conditions;

public class BankAccount {

    private int balance;
    private final int accountNumber;

    public BankAccount(int accountNumber, int balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount, int accountNumber){
        if(accountNumber != this.accountNumber){
            System.out.println( "Attempt to deposit to wrong account");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        int currentBalance = balance;
        currentBalance += amount;
        balance = currentBalance;
    }

    public void withdraw(int amount, int accountNumber){
        if(accountNumber != this.accountNumber){
            System.out.println( "Attempt to withdraw from wrong account");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (balance < amount) {
            System.out.println( "Insufficient funds");
        }
        int currentBalance = balance;
        currentBalance -= amount;
        balance = currentBalance;
    }

}
