package com.vladproduction.c03_synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * synchronizing by two different objects (lock1, lock2);
 * two threads paralleled on two cores;
 * we got twice less time as for Worker3().task() and job done the same;
 * we avoid race condition as well;
 * Program time: 4435
 * List1: 2000
 * List2: 2000
 */
public class TestSynchronizedMonitors4 {
    public static void main(String[] args) {
        new Worker4().task();
    }
}

class Worker4 {
    Random random = new Random();
    final Object lock1 = new Object();
    final Object lock2 = new Object();
    private final List<Integer> list1 = new ArrayList<>();
    private final List<Integer> list2 = new ArrayList<>();

    public void addToList1() {
        synchronized (lock1) {
            sleeping();
            list1.add(random.nextInt(100));
        }
    }

    public void addToList2() {
        synchronized (lock2) {
            sleeping();
            list2.add(random.nextInt(100));
        }
    }

    private static void sleeping() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
