package com.vladproduction.ch06_understanding_thread_interruption;

public class BasicInterruptionExample {
    public static void main(String[] args) throws InterruptedException {

        // Create a simple thread that counts
        Thread countingThread = new Thread(()->{
            int count = 0;
            // Check interruption status in the loop condition
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Counting: " + (++count));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // This is crucial: sleep() clears the interrupt flag when exception occurs
                    // We need to re-interrupt to maintain the interrupted status
                    Thread.currentThread().interrupt();
                    System.out.println("Thread was interrupted during sleep!");
                    break; // Exit the loop
                }
            }
            System.out.println("Thread finished counting.");
        });

        // Start the thread
        countingThread.start();

        // Let it run for a while
        Thread.sleep(5000); // Main thread sleeps for 5 seconds

        // Interrupt the counting thread
        System.out.println("Main thread: Interrupting the counting thread.");
        countingThread.interrupt();

        // Wait for counting thread to finish
        countingThread.join();
        System.out.println("Main thread: Program completed.");

    }
}
