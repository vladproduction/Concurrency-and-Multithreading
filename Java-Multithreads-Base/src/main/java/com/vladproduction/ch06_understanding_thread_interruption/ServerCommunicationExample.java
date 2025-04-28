package com.vladproduction.ch06_understanding_thread_interruption;

import java.util.concurrent.TimeUnit;

public class ServerCommunicationExample {

    // Volatile flag for signaling shutdown
    private static volatile boolean shutdownRequested = false;

    public static void main(String[] args) throws InterruptedException {
        // Create a clean shutdown hook for when program exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown hook: Requesting graceful shutdown");
            shutdownRequested = true;
        }));

        // Create thread to communicate with server
        Thread communicatorThread = new Thread(() -> {
            System.out.println("Communicator: Starting server communication");
            try {
                // Keep running until interrupted or shutdown requested
                while (!Thread.currentThread().isInterrupted() && !shutdownRequested) {
                    try {
                        doRequestToServer();
                    } catch (InterruptedException e) {
                        // Properly handle interruption by restoring interrupt flag
                        Thread.currentThread().interrupt();
                        System.out.println("Communicator: Thread was interrupted during request");
                        break;
                    }
                }
            } finally {
                // Important: Cleanup resources even if interrupted
                System.out.println("Communicator: Closing server connections and cleaning up");
                closeServerResources();
            }
            System.out.println("Communicator: Thread terminated");
        });

        // Name our thread for easier debugging
        communicatorThread.setName("ServerCommunicator");

        // Start the communication thread
        communicatorThread.start();

        // Create thread that monitors server status and can stop communicator
        Thread monitorThread = new Thread(() -> {
            System.out.println("Monitor: Starting server monitoring");
            try {
                // Give some time for communication to happen
                TimeUnit.SECONDS.sleep(7);

                if (isServerShouldBeOff()) {
                    System.out.println("Monitor: Server should be turned off");

                    // Request shutdown through flag (preferred, cooperative method)
                    shutdownRequested = true;

                    // Wait a bit for graceful shutdown
                    TimeUnit.SECONDS.sleep(2);

                    // If still running, use interruption (more direct)
                    if (communicatorThread.isAlive()) {
                        System.out.println("Monitor: Server still running, interrupting...");
                        communicatorThread.interrupt();
                    }

                    // Wait for thread to finish
                    communicatorThread.join(5000);

                    // If still running (worst case), we could use more drastic measures
                    // but NOT Thread.stop()!
                    if (communicatorThread.isAlive()) {
                        System.out.println("Monitor: WARNING - Server thread failed to terminate!");
                        // Log the issue, notify admins, etc.
                    } else {
                        System.out.println("Monitor: Server thread terminated successfully");
                        stopServerOperation();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Monitor: Monitoring was interrupted");
                Thread.currentThread().interrupt();
            }
        });

        // Name monitor thread
        monitorThread.setName("ServerMonitor");
        monitorThread.start();

        // Wait for both threads to complete
        communicatorThread.join();
        monitorThread.join();

        System.out.println("Main: Program completed");
    }

    private static void doRequestToServer() throws InterruptedException {
        // Check for interruption before starting
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Thread interrupted before server request");
        }

        // Communication logic
        System.out.println("Request to server sent ==>");
        try {
            TimeUnit.SECONDS.sleep(2); // Simulate server processing
        } catch (InterruptedException e) {
            System.out.println("Communication interrupted while waiting for response");
            // Handle partial communication - may need cleanup
            throw e; // Re-throw to notify caller
        }

        // Check again after potentially long operation
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Thread interrupted after receiving response");
        }

        System.out.println("<== Response received from server.\n");
    }

    private static boolean isServerShouldBeOff() {
        // Logic to determine if server should be shut down
        return true;
    }

    private static void stopServerOperation() {
        System.out.println("Server has been stopped, no response receiving available.");
    }

    private static void closeServerResources() {
        // Close any open resources: sockets, file handles, database connections, etc.
        System.out.println("Closing all server connections and resources");
        // In a real system, we would: close sockets, release database connections, etc.
    }
}
