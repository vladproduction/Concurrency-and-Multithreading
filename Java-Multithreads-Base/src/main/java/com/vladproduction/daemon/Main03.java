package com.vladproduction.daemon;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main03 {

    private static final String MESSAGE_DAEMON_STATUS_OF_THREAD = "%s : %s\n";
    public static void main(String[] args) throws InterruptedException {
        Thread firstDaemonThread = new Thread(()->{
           try{
               statusOfThread(currentThread());
               Thread secondDaemonThread = new Thread(() -> statusOfThread(currentThread()));
               secondDaemonThread.start();
               secondDaemonThread.join();
           }catch (InterruptedException interruptedException){
               currentThread().interrupt();
           }
        });
        firstDaemonThread.setDaemon(true);
        firstDaemonThread.start();

        firstDaemonThread.join();
    }

    private static void statusOfThread(Thread thread){

        out.printf(MESSAGE_DAEMON_STATUS_OF_THREAD, thread.getName(), thread.isDaemon());

    }
}
