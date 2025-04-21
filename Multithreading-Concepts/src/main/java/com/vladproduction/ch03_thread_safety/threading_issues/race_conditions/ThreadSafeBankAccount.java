package com.vladproduction.ch03_thread_safety.threading_issues.race_conditions;

public class ThreadSafeBankAccount {

    private volatile int balance;
    private final int accountNumber;

    public ThreadSafeBankAccount(int accountNumber, int balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public synchronized int getBalance() {
        return balance;
    }

    public synchronized void deposit(int amount, int accountNumber) {
        if (accountNumber != this.accountNumber) {
            System.out.println("Attempt to deposit to wrong account");
            return;
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }

    public synchronized void withdraw(int amount, int accountNumber) {
        if (accountNumber != this.accountNumber) {
            System.out.println("Attempt to withdraw from wrong account");
            return;
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (balance < amount) {
            System.out.println("Insufficient funds");
            return;
        }
        balance -= amount;
    }


}
