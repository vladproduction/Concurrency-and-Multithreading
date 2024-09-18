package com.vladproduction.c06_wait_notify;

import java.util.Scanner;

public class ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {

        WaitNotify waitNotify = new WaitNotify();

        Thread thread1 = new Thread(() -> {
            try {
                waitNotify.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                waitNotify.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();

        thread2.join();
    }
}

class WaitNotify{

    public void produce() throws InterruptedException {
        synchronized (this){
            System.out.println("Producer Thread started...");
            this.wait(); // 1)give away monitor; 2)and waiting when will be calling notify method
            System.out.println("Producer Thread resumed...");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(2000);
        Scanner scanner = new Scanner(System.in);
        synchronized (this){
            System.out.println("waiting for press ENTER key");
            scanner.nextLine();
            this.notify();
        }
    }

}

/**
 * 024 Паттерн producer - consumer II часть
 * */
