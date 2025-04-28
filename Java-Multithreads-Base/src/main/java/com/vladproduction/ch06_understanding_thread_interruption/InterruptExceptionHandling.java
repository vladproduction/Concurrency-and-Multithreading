package com.vladproduction.ch06_understanding_thread_interruption;

public class InterruptExceptionHandling {
    public static void main(String[] args) throws InterruptedException {

        Thread workerThread = new Thread(()->{
            try{
                System.out.println("Worker thread started a long task...");
                performLongRunningTask();
                System.out.println("Worker thread finished task.");
            } catch (InterruptedException e) {
                System.out.println("Worker: Task was interrupted!");
                // Good practice: Log the interruption if needed
                // But don't swallow the interruption!
            }

            // Check if we're still interrupted after catching InterruptedException
            if(Thread.currentThread().isInterrupted()){
                System.out.println("Worker: Thread is still marked as interrupted");
            } else {
                System.out.println("Worker: Thread is not marked as interrupted " +
                        "(InterruptedException clears the flag)");
            }
        });
        // Start the worker thread
        workerThread.start();

        // Let it run briefly
        Thread.sleep(3000);

        // Interrupt the worker
        System.out.println("Main: Interrupting worker thread");
        workerThread.interrupt();

        // Wait for worker to finish
        workerThread.join();
        System.out.println("Main: Program completed");

    }

    private static void performLongRunningTask() throws InterruptedException {
        // This simulates a task that periodically checks for interruption
        for (int i = 1; i < 10; i++) {
            // Proper pattern: check for interruption before doing work
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("Task interrupted at step " + i);
            }

            System.out.println("Working on step " + i);
            Thread.sleep(1000); // Sleep will throw InterruptedException if thread is interrupted
        }
    }

}
