package com.vladproduction.jvm_and_threads;

public class DaemonThreadExample {
    public static void main(String[] args) {

        Thread userThread = new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                System.out.println("User Thread: " + i);
                try{
                    Thread.sleep(1000L);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }, "User Thread");

        Thread daemonThread = new Thread(()->{
            while (true) {
                System.out.println("Daemon Thread is running.");
                try{
                    Thread.sleep(1000L);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }, "Daemon Thread");

        daemonThread.setDaemon(true);

        userThread.start();
        daemonThread.start();

    }
}
