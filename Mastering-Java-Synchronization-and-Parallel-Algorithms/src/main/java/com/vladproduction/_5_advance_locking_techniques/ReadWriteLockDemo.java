package com.vladproduction._5_advance_locking_techniques;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class SharedData {
    private int data;
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    // Method to read data
    public int read() {
        rwLock.readLock().lock(); // Acquire read lock
        try {
            return data;
        } finally {
            rwLock.readLock().unlock(); // Ensure read lock is released
        }
    }

    // Method to write data
    public void write(int value) {
        rwLock.writeLock().lock(); // Acquire write lock
        try {
            data = value;
        } finally {
            rwLock.writeLock().unlock(); // Ensure write lock is released
        }
    }
}

class WriterThread extends Thread {
    private SharedData sharedData;

    WriterThread(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            sharedData.write(i);
            System.out.println("Written: " + i);
            try {
                Thread.sleep(100); // Simulate writing delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class ReaderThread extends Thread {
    private SharedData sharedData;

    ReaderThread(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            int value = sharedData.read();
            System.out.println("Read: " + value);
            try {
                Thread.sleep(50); // Simulate reading delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        SharedData sharedData = new SharedData();

        // Create reader and writer threads
        WriterThread writer = new WriterThread(sharedData);
        ReaderThread reader = new ReaderThread(sharedData);

        // Start the threads
        writer.start();
        reader.start();

        try {
            // Wait for both threads to finish
            writer.join();
            reader.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }
    }
}
