package com.vladproduction.ch01_core_concepts.thread_basics;

// Example 3: Creating threads by extending Thread class
public class ThreadSubclassExample {

    static class WorkerThread extends Thread {
        private final int id;

        public WorkerThread(int id, String name) {
            super(name);
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("Worker #" + id + " started - " + getName());

            // Demonstrate thread states and priorities
            try {
                // Setting priority (1-10, higher means higher priority)
                setPriority(id % 2 == 0 ? MAX_PRIORITY : MIN_PRIORITY);
                System.out.println(getName() + " priority: " + getPriority());

                for (int i = 0; i < 3; i++) {
                    System.out.println(getName() + " executing task " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println(getName() + " was interrupted");
                return; // Exit the thread early
            }

            System.out.println(getName() + " completed execution");
        }
    }

    public static void main(String[] args) {
        System.out.println("Thread states demonstration:");

        WorkerThread thread1 = new WorkerThread(1, "LowPriority");
        WorkerThread thread2 = new WorkerThread(2, "HighPriority");

        // Thread is in NEW state
        System.out.println("Before starting: " + thread1.getName() +
                " state is " + thread1.getState());

        thread1.start(); // Thread moves to RUNNABLE state
        thread2.start();

        // Demonstrate checking thread state
        try {
            Thread.sleep(500);
            System.out.println("After starting: " + thread1.getName() +
                    " state is " + thread1.getState());

            // Demonstrate interrupting a thread
            if (Math.random() > 0.5) {
                System.out.println("Interrupting " + thread2.getName());
                thread2.interrupt();
            }

            // Wait for thread completion
            thread1.join();
            thread2.join();

            // Thread is now in TERMINATED state
            System.out.println("After completion: " + thread1.getName() +
                    " state is " + thread1.getState());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
