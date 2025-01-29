package com.vladproduction.barrier;

import com.vladproduction.loggingutil.LoggingUtil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Shopper extends Thread {

    public static int bagsOfChips = 1;
    private static final Lock lock = new ReentrantLock();
    private static final CyclicBarrier barrier = new CyclicBarrier(10);

    public Shopper(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        try {
            if (this.getName().contains("Olivia")) {
                processOlivia();
            } else { // "Bob"
                barrier.await();
                processBob();
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            LoggingUtil.logError("Error during barrier await in thread: " + this.getName(), e);
        }
    }

    private void processOlivia() {
        lock.lock();
        try {
            bagsOfChips += 3;
            LoggingUtil.logProcessInfo(this.getName() + " has added 3 bags of chips. Total: " + bagsOfChips);
        } finally {
            lock.unlock();
        }
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            LoggingUtil.logError("Error during barrier await for Olivia in thread: " + this.getName(), e);
        }
    }

    private void processBob() {
        lock.lock();
        try {
            bagsOfChips *= 2;
            LoggingUtil.logProcessInfo(this.getName() + " has doubled the bags of chips. Total: " + bagsOfChips);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Shopper("Olivia - " + i).start();
        }
        for (int i = 0; i < 5; i++) {
            new Shopper("Bob - " + i).start();
        }
    }
}
