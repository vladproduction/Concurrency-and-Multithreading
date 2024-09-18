package com.vladproduction.c03_synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * no monitors, only one thread do job in main method:
 * Program time: 4234
 * List1: 1000
 * List2: 1000
 */
public class TestSynchronizedMonitors {
    public static void main(String[] args) {

        new Worker().task();
    }
}

class Worker {
    Random random = new Random();
    private final List<Integer> list1 = new ArrayList<>();
    private final List<Integer> list2 = new ArrayList<>();

    public void addToList1() {
        sleep();
        list1.add(random.nextInt(100));
    }

    public void addToList2() {
        sleep();
        list2.add(random.nextInt(100));
    }

    private static void sleep() {
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
        work();
        long finish = System.currentTimeMillis();
        System.out.println("Program time: " + (finish - start));
        System.out.println("List1: " + list1.size());
        System.out.println("List2: " + list2.size());
    }
}
