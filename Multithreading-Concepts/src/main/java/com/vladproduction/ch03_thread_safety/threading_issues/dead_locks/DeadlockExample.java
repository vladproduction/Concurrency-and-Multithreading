package com.vladproduction.ch03_thread_safety.threading_issues.dead_locks;

public class DeadlockExample {

    private final Object resource1 = new Object();
    private final Object resource2 = new Object();

    public void worker1Function() {
        System.out.println("Worker1: Attempting to lock resource1...");
        synchronized (resource1) {
            System.out.println("Worker1: Locked resource1");
            try {
                Thread.sleep(100); // Simulate some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Worker1: Attempting to lock resource2...");
            synchronized (resource2) {
                System.out.println("Worker1: Locked resource2");
                // Critical section
                processResources("Worker1");
            }
        }
    }

    public void worker2Function() {
        System.out.println("Worker2: Attempting to lock resource2...");
        synchronized (resource2) {
            System.out.println("Worker2: Locked resource2");
            try {
                Thread.sleep(100); // Simulate some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Worker2: Attempting to lock resource1...");
            synchronized (resource1) {
                System.out.println("Worker2: Locked resource1");
                // Critical section
                processResources("Worker2");
            }
        }
    }

    private void processResources(String worker) {
        System.out.println(worker + ": Processing resources...");
    }

    public static void main(String[] args) {
        DeadlockExample deadlock = new DeadlockExample();

        Thread t1 = new Thread(() -> {
            while (true) {
                deadlock.worker1Function();
            }
        }, "Worker1");

        Thread t2 = new Thread(() -> {
            while (true) {
                deadlock.worker2Function();
            }
        }, "Worker2");

        System.out.println("Starting threads - Deadlock will occur...");
        t1.start();
        t2.start();
    }


}
