package com.vladproduction.countdownlatch;

import com.vladproduction.barrier.Shopper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShopperCDL extends Thread {

    public static int bagsOfChips = 1;
    private static final Lock lock = new ReentrantLock();
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(5);

    public ShopperCDL(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        try {
            if (this.getName().contains("Olivia")) {
                lock.lock();
                try {
                    bagsOfChips += 3;
                    System.out.println(this.getName() + " has added 3 bags of chips. Total: " + bagsOfChips);
                } finally {
                    lock.unlock();
                }
                COUNT_DOWN_LATCH.countDown();
            } else { // "Bob"
                COUNT_DOWN_LATCH.await(); // we can leave it as it is (from the barrier example)
                lock.lock();
                try {
                    bagsOfChips *= 2;
                    System.out.println(this.getName() + " has doubled the bags of chips. Total: " + bagsOfChips);
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            new ShopperCDL("Olivia - " + i).start();
        }

        for (int i = 0; i < 5; i++) {
            new ShopperCDL("Bob - " + i).start();
        }

    }

}
