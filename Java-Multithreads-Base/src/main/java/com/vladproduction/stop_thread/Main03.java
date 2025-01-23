package com.vladproduction.stop_thread;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main03 {

    private static final String MESSAGE_OF_REQUEST_SENT_TO_SERVER = "\nRequest was sent.";
    private static final int SLEEPING_TIME_AFTER_SENDING_REQUEST_TO_SERVER = 2;
    private static final String DELIVERY_REPORT_FROM_SERVER_THAT_MESSAGE_RECEIVED = "Response was received.";
    private static final String MESSAGE_THAT_SERVER_WAS_STOPPED = "\tSERVER_WAS_STOPPED";
    private static final long SLEEP_BEFORE_STOPPING_THREAD_START = 7;
    private static final String COMMUNICATING_THREAD_TO_SERVER_WAS_INTERRUPT = "Connection to server lost!";

    public static void main(String[] args) throws InterruptedException{

        Thread communicatingServerThread = new Thread(()->{
            try{
                while (!currentThread().isInterrupted()){
                    doRequest();
                }
            }catch (InterruptedException interruptedException){
                out.println(COMMUNICATING_THREAD_TO_SERVER_WAS_INTERRUPT);
            }
        });
        communicatingServerThread.start();

        Thread stoppingThread = new Thread(()->{
           if(isServerShouldBeOff()){
               communicatingServerThread.interrupt();
               stopServer();
           }
        });
        SECONDS.sleep(SLEEP_BEFORE_STOPPING_THREAD_START);

        stoppingThread.start();

    }

    private static void doRequest() throws InterruptedException{
        out.println(MESSAGE_OF_REQUEST_SENT_TO_SERVER);
        SECONDS.sleep(SLEEPING_TIME_AFTER_SENDING_REQUEST_TO_SERVER);
        out.println(DELIVERY_REPORT_FROM_SERVER_THAT_MESSAGE_RECEIVED);

    }

    private static boolean isServerShouldBeOff(){
        return true;
    }

    private static void stopServer(){
        out.println(MESSAGE_THAT_SERVER_WAS_STOPPED);
    }
}
