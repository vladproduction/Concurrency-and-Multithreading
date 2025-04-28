package com.vladproduction.ch26_countdownlatch.race_example;

import java.util.concurrent.*;

public class RaceSimulation {

    private static final int RUNNER_COUNT = 5;

    public static void main(String[] args) {

        // Latch for signaling that runners are ready at starting line
        CountDownLatch readyLatch = new CountDownLatch(RUNNER_COUNT);

        // Latch for starter's signal to begin the race
        CountDownLatch startLatch = new CountDownLatch(1);

        // Latch to wait for all runners to finish
        CountDownLatch finishLatch = new CountDownLatch(RUNNER_COUNT);

        ExecutorService executorService = Executors.newFixedThreadPool(RUNNER_COUNT);

        System.out.println("Race will begin soon!");

        // Create and start runners
        for (int i = 1; i <= RUNNER_COUNT; i++) {
            final int runnerId = i;
            executorService.submit(() -> {
                try {
                    // Runner getting ready and reaching the starting line
                    int preparationTime = ThreadLocalRandom.current().nextInt(1000, 5000);
                    System.out.println("Runner " + runnerId + " is getting ready...");
                    Thread.sleep(preparationTime);

                    System.out.println("Runner " + runnerId + " is at the starting line.");
                    readyLatch.countDown(); // Signal that this runner is ready

                    // Wait for starter's signal
                    startLatch.await();
                    System.out.println("Runner " + runnerId + " started running!");

                    // Run the race
                    int runningTime = ThreadLocalRandom.current().nextInt(3000, 10000);
                    Thread.sleep(runningTime);

                    System.out.println("Runner " + runnerId + " finished in " + runningTime/1000.0 + " seconds.");
                    finishLatch.countDown(); // Signal that this runner finished

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Starter thread
        new Thread(() -> {
            try {
                // Wait for all runners to get to the starting line
                System.out.println("Starter waiting for all runners to be ready...");
                readyLatch.await();

                System.out.println("All runners are at the starting line!");
                Thread.sleep(1000);
                System.out.println("Starter: Ready... Set... GO!");

                // Signal runners to start
                startLatch.countDown();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        // Race official waiting for results
        new Thread(() -> {
            try {
                // Wait for all runners to finish
                finishLatch.await();
                System.out.println("Race completed! All runners have finished.");

                executorService.shutdown();
                if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                    executorService.shutdownNow();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();


    }


}
