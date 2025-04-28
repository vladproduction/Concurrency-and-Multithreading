package com.vladproduction.ch06_understanding_thread_interruption;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class NonInterruptibleOperations {
    public static void main(String[] args) throws InterruptedException {

        Thread ioThread = new Thread(()-> {
            try{
                System.out.println("IO Thread: Starting non-interruptible operation");
                performNonInterruptibleIO();
                System.out.println("IO Thread: non-interruptible operation completed normally");
            } catch (IOException e){
                System.out.println("IO Thread: IO error: " + e.getMessage());
            } catch (InterruptedException e){
                System.out.println("IO Thread: Interrupted: " + e.getMessage());
            }
        });
        // Start the IO thread
        ioThread.start();

        // Wait briefly then interrupt
        Thread.sleep(100);
        System.out.println("Main: Interrupting IO thread");
        ioThread.interrupt();

        // Wait for IO thread to finish
        ioThread.join();
        System.out.println("Main: Program completed");


    }

    private static void performNonInterruptibleIO()throws IOException, InterruptedException{
        // Create a dummy file for demonstration
        File tempFile = File.createTempFile("example", ".tmp");
        tempFile.deleteOnExit();

        try(FileInputStream fis = new FileInputStream(tempFile)){
            byte[] buffer = new byte[1024];

            for (int i = 0; i < 10; i++) {
                //check for interruption before each operation
                if(Thread.currentThread().isInterrupted()){
                    throw new InterruptedException("Interrupted before IO operation");
                }

                // This operation cannot be interrupted directly
                int bytesRead = fis.read(buffer);
                System.out.println("Read " + bytesRead + " bytes");

                // after performing check again:
                if(Thread.currentThread().isInterrupted()){
                    throw new InterruptedException("Interrupted before IO operation");
                }

                //simulate processing work time
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // Re-interrupt the thread and propagate the exception
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }
        }
    }

}
