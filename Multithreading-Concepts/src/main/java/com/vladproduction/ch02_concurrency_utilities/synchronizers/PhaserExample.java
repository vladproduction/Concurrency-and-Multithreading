package com.vladproduction.ch02_concurrency_utilities.synchronizers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class PhaserExample {

    public static void main(String[] args) {
        int parties = 3;
        Phaser phaser = new Phaser(1); // Register self (main thread)
        ExecutorService executor = Executors.newFixedThreadPool(parties);

        for (int i = 0; i < parties; i++) {
            final int workerId = i;
            phaser.register(); // Register each worker

            executor.submit(() -> {
                try {
                    // Phase 1
                    System.out.println("Worker " + workerId + " starting phase 1");
                    Thread.sleep(1000);
                    System.out.println("Worker " + workerId + " finished phase 1");
                    phaser.arriveAndAwaitAdvance(); // Wait for all to complete phase 1

                    // Phase 2
                    System.out.println("Worker " + workerId + " starting phase 2");
                    Thread.sleep(1000);
                    System.out.println("Worker " + workerId + " finished phase 2");

                    // Worker is done with all phases
                    phaser.arriveAndDeregister();
                    System.out.println("Worker " + workerId + " has completed all work");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Main thread waits for all phases to complete
        phaser.arriveAndAwaitAdvance();
        System.out.println("All workers completed phase 1");

        phaser.arriveAndDeregister(); // Main thread is done
        executor.shutdown();
    }

}
