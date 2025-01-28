package com.vladproduction._8_barriers_and_coordination;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        final int numberOfThreads = 3;
        CyclicBarrier barrier = new CyclicBarrier(numberOfThreads, () -> {
            System.out.println("All parties have arrived at the barrier, let's proceed.");
        });

        // Create and start multiple threads
        for (int i = 1; i <= numberOfThreads; i++) {
            new Thread(new Task(barrier), "Thread " + i).start();
        }
    }
}
