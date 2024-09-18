package com.vladproduction.c03_synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * two threads paralleled on two cores;
 * we got twice bigger time as for Worker2().task() and job done the same;
 * we avoid race condition by adding to addToList methods synchronized key word;
 * Program time: 8550
 * List1: 2000
 * List2: 2000
 */
public class TestSynchronizedMonitors3 {
    public static void main(String[] args) {
        new Worker3().task();
    }
}

class Worker3 {
    Random random = new Random();
    private final List<Integer> list1 = new ArrayList<>();
    private final List<Integer> list2 = new ArrayList<>();

    public synchronized void addToList1() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        list1.add(random.nextInt(100));
    }

    public synchronized void addToList2() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        list2.add(random.nextInt(100));
    }

    public void work() {
        for (int i = 0; i < 1000; i++) {
            addToList1();
            addToList2();
        }
    }

    public void task() {
        long start = System.currentTimeMillis();

        //two threads build here:
        Thread thread1 = new Thread(this::work);
        Thread thread2 = new Thread(this::work);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long finish = System.currentTimeMillis();
        System.out.println("Program time: " + (finish - start));
        System.out.println("List1: " + list1.size());
        System.out.println("List2: " + list2.size());
    }
}
