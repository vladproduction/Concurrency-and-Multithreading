package com.vladproduction.ch03_thread_safety.threading_issues.race_conditions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BankApplication {
    public static void main(String[] args) throws InterruptedException {

        // Demonstrate race condition
        demonstrateRaceCondition();

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Demonstrate solution
        demonstrateSolution();


    }

    private static void demonstrateRaceCondition() throws InterruptedException {
        System.out.println("Demonstrating Race Condition:");
        BankAccount account = new BankAccount(1, 1000);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Simulate multiple concurrent transactions
        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    account.deposit(10, 1);
                    try {
                        Thread.sleep(1); // Increase chance of race condition
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    account.withdraw(10, 1);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("Final balance (should be 1000): " + account.getBalance());
        System.out.println("Balance is " + (account.getBalance() == 1000 ? "correct" : "incorrect") +
                " (Race condition caused inconsistency)");
    }

    private static void demonstrateSolution() throws InterruptedException {
        System.out.println("Demonstrating Solution with Thread-Safe Account:");
        ThreadSafeBankAccount account = new ThreadSafeBankAccount(1, 1000);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Simulate multiple concurrent transactions
        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    account.deposit(10, 1);
                    try {
                        Thread.sleep(1); // Same timing as before
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    account.withdraw(10, 1);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("Final balance (should be 1000): " + account.getBalance());
        System.out.println("Balance is " + (account.getBalance() == 1000 ? "correct" : "incorrect"));
    }



}
