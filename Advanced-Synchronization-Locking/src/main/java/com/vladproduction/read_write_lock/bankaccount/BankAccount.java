package com.vladproduction.read_write_lock.bankaccount;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BankAccount {

    private double balance;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        readLock.lock();
        try {
            return balance;
        }finally {
            readLock.unlock();
        }
    }
    public void deposit(double amount) {
        writeLock.lock();
        try{
            balance += amount;
            System.out.println("Deposited: $" + amount + ", New balance: $" + balance);
        }finally {
            writeLock.unlock();
        }
    }

    public void withdraw(double amount) {
        writeLock.lock();
        try {
            if(amount >= balance){
                balance -= amount;
                System.out.println("Withdrawn: $" + amount + ", New balance: $" + balance);
            }else {
                System.out.println("Withdrawn: $" + amount + "failed. Insufficient funds");
            }
        }finally {
            writeLock.unlock();
        }
    }

}
















