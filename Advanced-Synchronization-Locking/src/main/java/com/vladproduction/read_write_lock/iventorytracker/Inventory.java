package com.vladproduction.read_write_lock.iventorytracker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Inventory {

    private int itemCount;
    private final int maxItemCount;
    private final List<String> transactionHistory = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public Inventory(int itemCount, int maxItemCount) {
        this.itemCount = itemCount;
        this.maxItemCount = maxItemCount;
    }

    public int getItemCount() {
        readLock.lock();
        try {
            return itemCount;
        }finally {
            readLock.unlock();
        }
    }

    public void addItem(int quantity){
        writeLock.lock();
        try {
            //check if possible to add more items
            if(itemCount + quantity > maxItemCount){
                System.out.println("Cannot add more than " + maxItemCount + " items. Exceeds maximum capacity.");
                return;
            }
            itemCount += quantity;
            recordTransaction("Added: " + quantity + " items.");
            System.out.println("Added: " + quantity + " items. New Inventory: " + itemCount);
        }finally {
            writeLock.unlock();
        }
    }

    private void recordTransaction(String s) {
        transactionHistory.add(s);
    }

    public void removeItem(int quantity){
        writeLock.lock();
        try {
            //check if enough items for remove:
            if(quantity <= itemCount){
                itemCount -= quantity;
                recordTransaction("Removed: " + quantity + " items.");
                System.out.println("Removed: " + quantity + " items. New Inventory: " + itemCount);
            }else {
                System.out.println("Removal of " + quantity + " items failed. Insufficient inventory.");
            }
        }finally {
            writeLock.unlock();
        }
    }

    public List<String> getTransactionHistory() {
        readLock.lock();
        try {
            return new ArrayList<>(transactionHistory);
        }finally {
            readLock.unlock();
        }
    }

}
