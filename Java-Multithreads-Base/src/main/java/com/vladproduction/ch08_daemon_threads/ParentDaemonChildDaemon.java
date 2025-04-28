package com.vladproduction.ch08_daemon_threads;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;


/**
 *      The code creates the following thread hierarchy:
 * Main thread (non-daemon by default)
 * firstDaemonThread (explicitly set as daemon)
 * secondDaemonThread (created by firstDaemonThread)
 * */
public class ParentDaemonChildDaemon {

    private static final String MESSAGE_DAEMON_STATUS_OF_THREAD = "%s : %s\n";

    public static void main(String[] args) throws InterruptedException {

        out.println("Main thread started...");

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

        statusOfThread(currentThread());

        out.println("Main thread exiting.");
    }

    private static void statusOfThread(Thread thread){
        out.printf(MESSAGE_DAEMON_STATUS_OF_THREAD, thread.getName(), thread.isDaemon());
    }

    /**
     * If a parent thread is a daemon, any child thread created by it will also be a daemon by default.
     * This example perfectly demonstrates this inheritance relationship.
     * The secondDaemonThread automatically becomes a daemon thread because its parent (firstDaemonThread) is a daemon thread.
     * Important Details About Thread Daemon Status
     *
     * The daemon status of a thread is set before the thread is started.
     * Once a thread is started, you cannot change its daemon status (attempting to do so will throw an IllegalThreadStateException).
     * The main thread is always a non-daemon thread.
     * When all non-daemon threads finish execution, the JVM will terminate all daemon threads and exit.
     * Daemon threads are typically used for background tasks that don't need to complete, like garbage collection or system maintenance.
     * */
}
