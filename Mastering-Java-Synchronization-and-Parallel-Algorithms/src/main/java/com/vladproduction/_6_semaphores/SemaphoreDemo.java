package com.vladproduction._6_semaphores;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    public static void main(String[] args) {
        SharedPrinter printer = new SharedPrinter(2); // Two printers available

        // Create multiple print jobs
        PrintJob job1 = new PrintJob(printer, "Document 1");
        PrintJob job2 = new PrintJob(printer, "Document 2");
        PrintJob job3 = new PrintJob(printer, "Document 3");
        PrintJob job4 = new PrintJob(printer, "Document 4");

        // Start the print jobs
        job1.start();
        job2.start();
        job3.start();
        job4.start();

        try {
            // Wait for all print jobs to finish
            job1.join();
            job2.join();
            job3.join();
            job4.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }
    }
}
