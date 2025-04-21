package com.vladproduction.ch04_advanced_concepts.memory_model;

// Fixed version using synchronization
public class SynchronizedMemoryModel {

    private static boolean stopRequested = false;

    private static synchronized void requestStop() {
        stopRequested = true;
    }

    private static synchronized boolean isStopRequested() {
        return stopRequested;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            // This will properly terminate because synchronization
            // ensures memory visibility
            while (!isStopRequested()) {
                i++;
            }
            System.out.println("Background thread stopped after " + i + " iterations");
        });

        backgroundThread.start();

        Thread.sleep(1000);
        requestStop(); // Properly synchronized, will be visible to background thread

        System.out.println("Main thread set stopRequested to true");
    }

}
