package com.vladproduction.ch02_sleep_join.two_threads_count_1000;

// client code with threads creating and starting
//also provided functionality to sleep or/and join threads
public class MainApp {

    //static variables:
    private static final int TIME_THREAD_TO_SLEEP_CONSTANT = 1000;
    private static final int FIRST_THREAD_FROM_NUMBER = 1;
    private static final int FIRST_THREAD_TO_NUMBER = 5000;
    private static final int SECOND_THREAD_FROM_NUMBER = 5001;
    private static final int SECOND_THREAD_TO_NUMBER = 10000;

    public static void main(String[] args) {
        System.out.println("MainApp starting...");

        //create tasks and start them
        TaskSumNumbers task1 = new TaskSumNumbers(FIRST_THREAD_FROM_NUMBER, FIRST_THREAD_TO_NUMBER);
        Thread thread1 = new Thread(task1);
        thread1.start();

        TaskSumNumbers task2 = new TaskSumNumbers(SECOND_THREAD_FROM_NUMBER, SECOND_THREAD_TO_NUMBER);
        Thread thread2 = new Thread(task2);
        thread2.start();

        //version #1 (sleep):
        waitForTasksFinished_usedSleep(thread1, thread2);

        //version #2 (join)
        waitForTasksFinished_usedJoin(thread1, thread2);

        int result = task1.getSum() + task2.getSum();
        System.out.println("MainApp finished with final result: " + result);

    }

    //helper methods:
    private static void waitForTasksFinished_usedSleep(Thread... threads) {
        for (Thread thread : threads) {
            try {
                Thread.sleep(TIME_THREAD_TO_SLEEP_CONSTANT);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void waitForTasksFinished_usedJoin(Thread... threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
