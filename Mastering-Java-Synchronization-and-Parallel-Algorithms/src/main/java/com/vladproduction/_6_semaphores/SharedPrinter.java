package com.vladproduction._6_semaphores;

import java.util.concurrent.Semaphore;

class SharedPrinter {
    private final Semaphore semaphore;

    SharedPrinter(int numberOfPrinters) {
        // Initialize the semaphore with the number of available printers
        this.semaphore = new Semaphore(numberOfPrinters);
    }

    public void printDocument(String document) {
        try {
            // Acquire a permit before printing
            semaphore.acquire();
            System.out.println("Printing: " + document);
            Thread.sleep(2000); // Simulate printing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Printing interrupted.");
        } finally {
            // Release the permit after printing
            semaphore.release();
            System.out.println("Done printing: " + document);
        }
    }
}
