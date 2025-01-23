package com.vladproduction.concepts_of_threading_in_Java;

/**
 * Approach #2: Shared Memory Space
 * Concept:
 *
 * Threads within the same process share the same memory space, which allows for simpler communication and data sharing compared
 * to processes that require inter-process communication (IPC).
 * */
public class SharedMemoryExample {
    private static int sharedCounter = 0; // Shared memory

    public static void main(String[] args) {
        Thread thread1 = new Thread(new IncrementTask(), "Thread-1");
        Thread thread2 = new Thread(new IncrementTask(), "Thread-2");

        thread1.start();
        thread2.start();

        // In this example, both threads increment a shared counter.
        // The incrementCounter method is synchronized to prevent concurrent modifications,
        // showing how threads can safely share and modify the same memory resource.
    }

    static class IncrementTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                incrementCounter();
            }
        }

        private synchronized void incrementCounter() {
            sharedCounter++;
            System.out.println(Thread.currentThread().getName() + " - Counter: " + sharedCounter);
            try {
                Thread.sleep(300); // Simulating work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
