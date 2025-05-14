package com.vladproduction.profiling_multithreaded_app;

public class BankingApp {
    public static void main(String[] args) {

        BankAccount account = new BankAccount();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                account.deposit(100);
                account.withdraw(50);
            }
        };

        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Final balance: " + account.getBalance());

    }
}
