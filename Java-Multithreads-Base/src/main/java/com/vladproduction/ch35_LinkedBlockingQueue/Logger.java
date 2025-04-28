package com.vladproduction.ch35_LinkedBlockingQueue;

/**
 * Simple utility class to log messages.
 * */
public class Logger {

    /**
     * Log a message with timestamp and thread information
     * @param message The message to log
     */
    public static void log(String message){
        System.out.printf("[%s] [%s] %s%n",
                System.currentTimeMillis(),
                Thread.currentThread().getName(),
                message
        );
    }



}
