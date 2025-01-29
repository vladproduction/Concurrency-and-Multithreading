package com.vladproduction.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    public static void main(String[] args) throws InterruptedException {
        Shopper customer1 = new Shopper();
        Shopper customer2 = new Shopper();

        customer1.start();
        customer2.start();

        customer1.join();
        customer2.join();

        System.out.println("We should buy: " + Shopper.garlicCount + " garlics");
        System.out.println("We should buy: " + Shopper.potatoCount + " potatoes");

    }
}

class Shopper extends Thread {

    static int garlicCount = 0;
    static int potatoCount = 0;
    static ReentrantLock pencil = new ReentrantLock(); // to more concrete implementation we can use some specific methods

    private void addGarlic() {
        pencil.lock();
        System.out.println("Hold count: " + pencil.getHoldCount()); // 1-directly call; 2-inside the other method which is has locks too
        garlicCount++;
        pencil.unlock();
    }

    private void addPotato() {
        pencil.lock();
        potatoCount++;
        addGarlic();
        pencil.unlock();
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            addGarlic();
            addPotato();
        }
    }
}
