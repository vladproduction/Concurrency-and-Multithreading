package com.vladproduction.ch05_interrupting_threads;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Demonstrates client-server communication with user notification on interruption.
 * This version prioritizes notifying the user over preserving interrupt status.
 */
public class ClientServerCommunicationWithNotification {

    // Constants for messages with clearer naming
    private static final String CLIENT_REQUEST_SENT = "\nClient request sent to server";
    private static final String SERVER_RESPONSE_RECEIVED = "Server response received";
    private static final String SERVER_SHUTDOWN_MESSAGE = "\tSERVER SHUTDOWN COMPLETE";
    private static final String CONNECTION_LOST_MESSAGE = "Connection to server lost!";

    // Configuration constants
    private static final int REQUEST_PROCESSING_TIME_SECONDS = 2;
    private static final int RUNTIME_BEFORE_SHUTDOWN_SECONDS = 7;

    public static void main(String[] args) throws InterruptedException {
        // Create and start the client communication thread
        Thread clientThread = new Thread(() -> {
            try {
                // Continue processing requests until interrupted
                while (!currentThread().isInterrupted()) {
                    sendRequestAndReceiveResponse();
                }
            } catch (InterruptedException e) {
                // This version prioritizes notifying the user of the interruption
                // Note: The interrupted status is NOT restored here, which is a key difference
                out.println(CONNECTION_LOST_MESSAGE);

                // In a real application, you might want to do additional cleanup here
            }
        });
        clientThread.start();

        // Create the shutdown monitor thread
        Thread shutdownThread = new Thread(() -> {
            if (shouldInitiateShutdown()) {
                // Signal the client thread to stop
                clientThread.interrupt();
                // Perform server shutdown operations
                shutdownServer();
            }
        });

        // Run the system for specified time before initiating shutdown
        SECONDS.sleep(RUNTIME_BEFORE_SHUTDOWN_SECONDS);
        shutdownThread.start();
    }


    /**
     * Simulates sending a request to the server and receiving a response.
     * This method is interruptible during the wait for server response.
     */
    private static void sendRequestAndReceiveResponse() throws InterruptedException {
        out.println(CLIENT_REQUEST_SENT);
        // Simulate network delay - this makes the method interruptible
        SECONDS.sleep(REQUEST_PROCESSING_TIME_SECONDS);
        out.println(SERVER_RESPONSE_RECEIVED);
    }

    /**
     * Determines if the server should be shut down based on criteria.
     * In a real application, this might check system load, time of day, etc.
     */
    private static boolean shouldInitiateShutdown() {
        // Simplified logic - in a real system, would have actual conditions
        return true;
    }

    /**
     * Performs necessary cleanup operations when shutting down the server.
     */
    private static void shutdownServer() {
        // In a real application, would close resources, flush data, etc.
        out.println(SERVER_SHUTDOWN_MESSAGE);
    }

}
