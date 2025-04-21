package com.vladproduction.ch04_advanced_concepts.memory_model;

public class MemoryModelExample {

    // This example demonstrates how threads can see stale values
    private static boolean stopRequested = false;

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            // This might run forever because the thread may never see
            // the updated value of stopRequested from main thread
            while (!stopRequested) {
                i++;
            }
            System.out.println("Background thread stopped after " + i + " iterations");
        });

        backgroundThread.start();

        Thread.sleep(1000);
        stopRequested = true; // May never be visible to the background thread

        System.out.println("Main thread set stopRequested to true");
    }

}
