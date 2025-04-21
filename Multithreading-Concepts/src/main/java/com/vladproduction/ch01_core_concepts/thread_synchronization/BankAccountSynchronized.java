package com.vladproduction.ch01_core_concepts.thread_synchronization;

// Example 1: Race Condition and Synchronized Methods
public class BankAccountSynchronized {

    private double balance;
    private final String accountNumber;

    public BankAccountSynchronized(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // Synchronized method to prevent race conditions
    public synchronized void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        // Simulate some processing time
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        double newBalance = balance + amount;
        balance = newBalance;

        System.out.println(Thread.currentThread().getName() +
                " deposited " + amount +
                " | New balance: " + balance);
    }

    // Synchronized method for withdrawal
    public synchronized boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (balance < amount) {
            System.out.println(Thread.currentThread().getName() +
                    " failed to withdraw " + amount +
                    " | Insufficient funds: " + balance);
            return false;
        }

        // Simulate some processing time
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        balance -= amount;
        System.out.println(Thread.currentThread().getName() +
                " withdrew " + amount +
                " | New balance: " + balance);
        return true;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public static void main(String[] args) {
        BankAccountSynchronized account = new BankAccountSynchronized("123456789", 1000.0);

        // Create multiple threads that deposit and withdraw concurrently
        Runnable depositTask = () -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(100.0);
            }
        };

        Runnable withdrawTask = () -> {
            for (int i = 0; i < 5; i++) {
                account.withdraw(100.0);
            }
        };

        Thread depositThread1 = new Thread(depositTask, "DepositThread-1");
        Thread depositThread2 = new Thread(depositTask, "DepositThread-2");
        Thread withdrawThread1 = new Thread(withdrawTask, "WithdrawThread-1");
        Thread withdrawThread2 = new Thread(withdrawTask, "WithdrawThread-2");

        depositThread1.start();
        withdrawThread1.start();
        depositThread2.start();
        withdrawThread2.start();

        try {
            depositThread1.join();
            depositThread2.join();
            withdrawThread1.join();
            withdrawThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final account balance: " + account.getBalance());
    }

}
