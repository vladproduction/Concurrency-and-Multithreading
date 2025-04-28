package com.vladproduction.ch05_interrupting_threads;

import java.util.concurrent.TimeUnit;

public class InterruptingThreadExample {

    public static final String REQUEST_TO_SERVER_SENT = "Request to server sent.";
    public static final int TIMEOUT_DO_REQUEST_SERVER = 2;
    public static final String RESPONSE_FROM_SERVER_RECEIVED = "Response from server received.\n";
    public static final String SERVER_BEEN_STOPPED_NO_RESPONSE_RECEIVING_AVAILABLE = "Server been stopped, no response receiving available.";
    public static final String THREAD_WAS_INTERRUPTED_DURING_SERVER_COMMUNICATION = "Thread was interrupted during server communication";
    public static final int TIMEOUT_BEFORE_STOPPER_THREAD_STARTING = 7;
    public static final String CLEANING_UP_SERVER_COMMUNICATION_RESOURCES = "Cleaning up server communication resources";
    public static final String SERVER_COMMUNICATION_THREAD_WAS_INTERRUPTED = "Server communication thread was interrupted";

    public static void main(String[] args) throws InterruptedException {

        //create thread to communicate with server and do requests (while loop)
        Thread thread_server_communicator = new Thread(()->{
            try{
                while(!Thread.currentThread().isInterrupted()){
                    doRequestToServer();
                }
            }catch (InterruptedException e){
                // Log the interruption if needed
                System.out.println(SERVER_COMMUNICATION_THREAD_WAS_INTERRUPTED);
                // No need to print stack trace for normal interruption
                // e.printStackTrace();
            } finally {
                System.out.println(CLEANING_UP_SERVER_COMMUNICATION_RESOURCES);
                // Perform any necessary cleanup here
            }
        });
        thread_server_communicator.start();

        //create thread that able to stop communicator, and the program should be terminated
        Thread thread_stopper = new Thread(()->{
            if(isServerShouldBeOff()){
                thread_server_communicator.interrupt(); // Using interrupt instead of stop
                stopServerOperation();
            }
        });
        //sleep before starting thread_stopper, make thread action for a while
        TimeUnit.SECONDS.sleep(TIMEOUT_BEFORE_STOPPER_THREAD_STARTING);
        thread_stopper.start();
    }

    private static void doRequestToServer() throws InterruptedException {
        //messaging scenario
        //1. message sent to server
        //2. sleeping to simulate server processing request
        //3. message sent back from server
        System.out.println(REQUEST_TO_SERVER_SENT);
        TimeUnit.SECONDS.sleep(TIMEOUT_DO_REQUEST_SERVER);
        System.out.println(RESPONSE_FROM_SERVER_RECEIVED);

        // Check for interruption after potentially long operations
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException(THREAD_WAS_INTERRUPTED_DURING_SERVER_COMMUNICATION);
        }
    }

    private static boolean isServerShouldBeOff() {
        return true;
    }

    private static void stopServerOperation() {
        System.out.println(SERVER_BEEN_STOPPED_NO_RESPONSE_RECEIVING_AVAILABLE);
    }

}
