package com.vladproduction.profiling_multithreaded_app;

import java.util.concurrent.TimeUnit;

public class BankAccount {

    private int balance = 0;

    public synchronized void deposit(int amount){
        balance += amount;
        try {
            TimeUnit.MILLISECONDS.sleep(100L);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void withdraw(int amount){
        if(balance >= amount){
            balance -= amount;
            try {
                TimeUnit.MILLISECONDS.sleep(100L);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized int getBalance(){
        return balance;
    }

}
