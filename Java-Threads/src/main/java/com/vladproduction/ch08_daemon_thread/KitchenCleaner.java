package com.vladproduction.ch08_daemon_thread;

public class KitchenCleaner extends Thread{

    @Override
    public void run() {
        while (true){
            System.out.println("Olivia cleaned the kitchen.");
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
