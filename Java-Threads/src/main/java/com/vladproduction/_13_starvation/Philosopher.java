package com.vladproduction._13_starvation;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This example illustrates how improper resource acquisition order or unfair scheduling can lead to starvation,
 * where one or more threads (philosophers) are unable to proceed because they're always getting denied the necessary
 * locks to continue their operations. For a real program, far more sophisticated techniques should be used
 * to handle such scenarios and avoid starvation.
 * */
class Philosopher extends Thread {
    private final Lock leftFork;
    private final Lock rightFork;
    private static int totalEatCount = 0;

    public Philosopher(String name, Lock leftFork, Lock rightFork) {
        super(name);
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        while (totalEatCount < 10) { // Each philosopher tries to eat 10 times
            try {
                // Attempt to pick up the left fork
                leftFork.lock();
                // Attempt to pick up the right fork
                rightFork.lock();

                // Now both forks are acquired, the philosopher can eat
                System.out.println(this.getName() + " is eating.");
                totalEatCount++;

                // Simulating eating process
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                // Always release the forks
                rightFork.unlock();
                leftFork.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Lock fork1 = new ReentrantLock();
        Lock fork2 = new ReentrantLock();
        Lock fork3 = new ReentrantLock();
        Lock fork4 = new ReentrantLock();
        Lock fork5 = new ReentrantLock();

        Philosopher p1 = new Philosopher("Philosopher 1", fork1, fork2);
        Philosopher p2 = new Philosopher("Philosopher 2", fork2, fork3);
        Philosopher p3 = new Philosopher("Philosopher 3", fork3, fork4);
        Philosopher p4 = new Philosopher("Philosopher 4", fork4, fork5);
        Philosopher p5 = new Philosopher("Philosopher 5", fork5, fork1); // Circular dependency

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
    }
}

/*Explanation:
Philosophers: Each philosopher continuously tries to eat by acquiring their left and right forks. The forks are represented as locks.
Starvation Scenario: If Philosopher 1 and Philosopher 2 are always trying to eat first (for some reason, like being scheduled more frequently),
Philosopher 3, Philosopher 4, and Philosopher 5 can starve. They may never get a chance to acquire both necessary forks due to the continuous
acquisition by the first two.
Sleeping: Each philosopher sleeps for a short period after 'eating' but they always try to lock the forks, which may lead to situations where
they keep retrying giving up their opportunity.*/
