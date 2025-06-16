package com.vladproduction.ch12_deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PersonAbandonLocked2 extends Thread{
    private final Lock stick1;
    private final Lock stick2;
    private static int sushi = 500;

    public PersonAbandonLocked2(String name, Lock stick1, Lock stick2) {
        super(name);
        this.stick1 = stick1;
        this.stick2 = stick2;
    }

    @Override
    public void run() {
        while (sushi > 0) { // eat sushi until it's all gone
            boolean acquiredLock = false;

            try {
                // Attempt to acquire both locks
                stick1.lock();
                stick2.lock();
                acquiredLock = true;

                // take a piece of sushi
                if (sushi > 0) {
                    sushi--;
                    System.out.println(this.getName() + " took a piece of sushi. Sushi remaining: " + sushi);
                }

                if (sushi == 10) {
                    System.out.println(1 / 0); // Simulating an error
                }
            } catch (Exception e) {
                System.err.println(this.getName() + " encountered an error: " + e.getMessage());
            } finally {
                // Always unlock if the locks were acquired
                if (acquiredLock) {
                    stick1.unlock();
                    stick2.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        Lock stick1 = new ReentrantLock();
        Lock stick2 = new ReentrantLock();
        Lock stick3 = new ReentrantLock();

        new PersonAbandonLocked2("John", stick1, stick2).start();
        new PersonAbandonLocked2("Bob", stick2, stick3).start();
        new PersonAbandonLocked2("Peter", stick1, stick3).start();
    }
}
