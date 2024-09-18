package com.vladproduction.c02_volatile;

import java.util.Scanner;

public class VolatileDemo {
    public static void main(String[] args) {

        MyThread myThread1 = new MyThread();
        myThread1.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        myThread1.shutdown();

    }

    static  class MyThread extends Thread{

        //to avoid coherency problem (caching data in processor memory)
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running){
                System.out.println("Hello");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void shutdown(){
            this.running = false;
        }
    }
}
