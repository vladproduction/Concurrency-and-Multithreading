package com.vladproduction.ch02_sleep_join.many_threads_join;

/**
 * This class demonstrates the use of Thread.join() method to wait for multiple threads to complete
 * before continuing execution in the main thread. It simulates a race between players with different speeds.
 */
public class ManyThreadsJoin {
    public static void main(String[] args) throws InterruptedException {

        // Create players with different speeds (distance/speed = time to finish)
        // Each player has the same distance (100 m) but different speeds
        Player4Join p1 = new Player4Join("P1", 100, 11); // Fastest player (9.09 seconds)
        Player4Join p2 = new Player4Join("P2", 100, 10); // (10 seconds)
        Player4Join p3 = new Player4Join("P3", 100, 9);  // (11.11 seconds)
        Player4Join p4 = new Player4Join("P4", 100, 8);  // Slowest player (12.5 seconds)

        // Record the start time to measure total execution time
        long startTime = System.currentTimeMillis();
        System.out.println("startTime = " + startTime);

        // Start all player threads - they will execute concurrently
        p1.start();
        p2.start();
        p3.start();
        p4.start();

        // Wait for all threads to complete using join()
        // This ensures the main thread will only continue after all players finish
        p1.join();
        p2.join();
        p3.join();
        p4.join();

        // After all threads complete, record the finish time
        long finishTime = System.currentTimeMillis();
        System.out.println("finishTime = " + finishTime);

        // Calculate and display total execution time
        long time = finishTime - startTime;
        System.out.println("time = " + time);

        // Note: The total execution time will be approximately as long as the slowest thread (p4)
        // since we wait for all threads to complete with join()
    }
}

