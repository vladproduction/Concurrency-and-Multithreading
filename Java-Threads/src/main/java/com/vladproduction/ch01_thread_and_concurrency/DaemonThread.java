package com.vladproduction.ch01_thread_and_concurrency;

/**
 * Daemon Thread is:
 * Infrastructure threads that run in the background,
 * performing tasks like garbage collection or GUI event dispatching, are known as daemon threads.
 * */
public class DaemonThread extends Thread{
    public static void main(String[] args) {

        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true); //have to set it as true --> now it is daemon thread;
        //now we can start daemon thread
        daemonThread.start();

        //create some logic in the main thread also:
        //The main method sleeps for 3 seconds and then prints a completion message.
        // Once the main thread finishes, the daemon thread is terminated automatically.
        System.out.println("Main method is running...");
        try {
            Thread.sleep(3000); // Main thread sleeps for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main method completed. Exiting...");
    }

    //method, it contains a loop that keeps printing a message every second.
    @Override
    public void run() {
        //simulating long timing work to do by this thread
        while (true){
            System.out.println("Daemon thread is running...");
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Daemon thread is interrupted.");
            }
        }
    }
}
