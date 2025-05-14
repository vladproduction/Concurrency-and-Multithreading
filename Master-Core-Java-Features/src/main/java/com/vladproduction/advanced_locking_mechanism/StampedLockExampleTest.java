package com.vladproduction.advanced_locking_mechanism;

import java.util.concurrent.TimeUnit;

public class StampedLockExampleTest {
    public static void main(String[] args) {

        BankAccount account = new BankAccount();

        Runnable depositTask = () -> {
            for (int i = 1; i <= 3; i++) {
                account.deposit(100);
                try{
                    TimeUnit.MILLISECONDS.sleep(500L);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable withdrawTask = () -> {
            for (int i = 1; i <= 3; i++) {
                account.withdraw(50);
                try{
                    TimeUnit.MILLISECONDS.sleep(500L);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable readTask = () -> {
            for (int i = 1; i <= 3; i++) {
                account.getBalanceOptimistic();
                try{
                    TimeUnit.MILLISECONDS.sleep(500L);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread depositorThread = new Thread(depositTask, "Depositor");
        Thread withdrawerThread = new Thread(withdrawTask, "Withdrawer");
        Thread readerThread = new Thread(readTask, "Reader");

        depositorThread.start();
        withdrawerThread.start();
        readerThread.start();

        try{
            depositorThread.join();
            withdrawerThread.join();
            readerThread.join();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        System.out.println("\n\tFinal balance: " + account.getBalance());

    }
}















