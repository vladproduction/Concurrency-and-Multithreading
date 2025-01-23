package com.vladproduction.stop_thread;

import static java.lang.System.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main01 {

    private static final String MESSAGE_OF_REQUEST_SENT_TO_SERVER = "\nRequest was sent.";
    private static final int SLEEPING_TIME_AFTER_SENDING_REQUEST_TO_SERVER = 2;
    private static final String DELIVERY_REPORT_FROM_SERVER_THAT_MESSAGE_RECEIVED = "Response was received.";
    private static final String MESSAGE_THAT_SERVER_WAS_STOPPED = "\tSERVER_WAS_STOPPED";
    private static final long SLEEP_BEFORE_STOPPING_THREAD_START = 7;

    public static void main(String[] args) throws InterruptedException{

        /*Thread communicatingServerThread = new Thread(()->{
            try{
                while (true){
                    doRequest();
                }
            }catch (InterruptedException interruptedException){
                Thread.currentThread().interrupt();
            }
        });

        communicatingServerThread.start();

        Thread stoppingThread = new Thread(()->{
           if(isServerShouldBeOff()){
               communicatingServerThread.stop();
               stopServer();
           }
        });
        SECONDS.sleep(SLEEP_BEFORE_STOPPING_THREAD_START);

        stoppingThread.start();*/

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
