package com.vladproduction._8_barriers_and_coordination;

import java.util.concurrent.CyclicBarrier;

class Task implements Runnable {
    private final CyclicBarrier barrier;

    Task(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " is doing some work.");
            Thread.sleep((long) (Math.random() * 1000)); // Simulate work

            // Wait at the barrier
            System.out.println(Thread.currentThread().getName() + " has reached the barrier.");
            barrier.await(); // Wait for others to reach the barrier

            // Once all threads have reached the barrier, they proceed
            System.out.println(Thread.currentThread().getName() + " is proceeding after the barrier.");
        } catch (Exception e) {
            System.out.println("Exception in " + Thread.currentThread().getName() + ": " + e.getMessage());
        }
    }
}
