package com.vladproduction.c03_synchronize;

/**
 * two threads are synchronized;
 * only one thread at time has ability to use method body which marked synchronized;
 * doWork() and increment() should be public, (as private it does not work);
 * */
public class TestSynchronized {
    private int counter;

    public static void main(String[] args) throws InterruptedException {
        TestSynchronized test = new TestSynchronized();
        test.doWork();
    }

    public synchronized void increment() {
        counter++;
    }
//    public void increment() {
//        counter++; //not atomicity operation
//    }

    public void doWork() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 30000; i++) {
                increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 20000; i++) {
                increment();
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(counter);
    }
}
