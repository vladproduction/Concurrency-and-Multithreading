package com.vladproduction.daemon;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;

public class Main04 {
    private static final String MESSAGE_DAEMON_STATUS_OF_THREAD = "%s : %s\n";

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
           statusOfThread(currentThread());
        });
        thread.start();
        thread.setDaemon(true);

    }

    private static void statusOfThread(Thread thread){

        out.printf(MESSAGE_DAEMON_STATUS_OF_THREAD, thread.getName(), thread.isDaemon());

    }
}
