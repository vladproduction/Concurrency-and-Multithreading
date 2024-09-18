package com.vladproduction.c03_synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * two threads paralleled on two cores;
 * we got same time as for Worker().task() but job done twice bigger:
 * Program time: 3854
 * List1: 1977
 * List2: 1982;
 */
public class TestSynchronizedMonitors2 {
    public static void main(String[] args) {
        new Worker2().task();
    }
}

class Worker2 {
    Random random = new Random();
    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    public void addToList1() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list1.add(random.nextInt(100));
    }

    public void addToList2() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
