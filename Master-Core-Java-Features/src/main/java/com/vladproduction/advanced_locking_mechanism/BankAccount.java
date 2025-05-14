package com.vladproduction.advanced_locking_mechanism;

import java.util.concurrent.locks.StampedLock;

public class BankAccount {

    private double balance = 0;
    private final StampedLock lock = new StampedLock();

    public void deposit(double amount) {
        long stamp = lock.writeLock();
        try{
            System.out.println(Thread.currentThread().getName() + " deposit: " + amount);
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " new balance: " + balance);
        }finally {
            lock.unlockWrite(stamp);
        }
    }

    public void withdraw(double amount) {
        long stamp = lock.writeLock();
        try{
            if(balance >= amount){
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + " withdraw: " + amount + ", new balance: " + balance);
            }else {
                System.out.println(Thread.currentThread().getName() + " withdrawing: " + amount + ", Insufficient balance.");
            }
        }finally {
            lock.unlockWrite(stamp);
        }
    }

    public void getBalanceOptimistic() {
        long stamp = lock.tryOptimisticRead();
        double currentBalance = balance;

        if(!lock.validate(stamp)){
            stamp = lock.readLock();
            try{
                currentBalance = balance;
            }finally {
                lock.unlockRead(stamp);
            }
        }
        System.out.println(Thread.currentThread().getName() + ": Reading the balance: " + currentBalance);
    }

    public double getBalance() {
        long stamp = lock.readLock();
        try{
            return balance;
        }finally {
            lock.unlockRead(stamp);
        }
    }



}


















