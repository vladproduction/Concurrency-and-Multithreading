package com.vladproduction.ch12_deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PersonAbandonLock extends Thread {

    private final Lock stick1;
    private final Lock stick2;
    private static int sushi = 500;

    public PersonAbandonLock(String name, Lock stick1, Lock stick2) {
        super(name);
        this.stick1 = stick1;
        this.stick2 = stick2;
    }

    @Override
    public void run() {
        while (sushi > 0) { // eat sushi until it's all gone
            boolean acquiredLock1 = false;
            boolean acquiredLock2 = false;
            try {
                // Try to pick up both locks
                acquiredLock1 = stick1.tryLock();
                acquiredLock2 = stick2.tryLock();

                // Check if we've acquired both locks
                if (acquiredLock1 && acquiredLock2) {
                    try {
                        // take a piece of sushi
                        if (sushi > 0) {
                            sushi--;
                            // print info of remaining pieces
                            System.out.println(this.getName() + " took a piece of sushi. Sushi remaining: " + sushi);
                        }
                        if (sushi == 10) {
                            System.out.println(1 / 0); // Simulating an error
                        }
                    } catch (Exception e) {
                        System.err.println(this.getName() + " encountered an error: " + e.getMessage());
                    }
                } else {
                    // If failed to acquire both locks, we can handle it or retry
                    System.out.println(this.getName() + " couldn't acquire both sticks, retrying...");
                }
            } finally {
                // Always unlock if the locks were acquired
                if (acquiredLock1) {
                    stick1.unlock();
                }
                if (acquiredLock2) {
                    stick2.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        Lock stick1 = new ReentrantLock();
        Lock stick2 = new ReentrantLock();
        Lock stick3 = new ReentrantLock();

        new PersonAbandonLock("John", stick1, stick2).start();
        new PersonAbandonLock("Bob", stick2, stick3).start();
        new PersonAbandonLock("Peter", stick1, stick3).start();
    }
}
