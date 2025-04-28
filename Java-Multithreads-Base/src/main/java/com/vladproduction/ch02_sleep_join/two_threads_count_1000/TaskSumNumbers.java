package com.vladproduction.ch02_sleep_join.two_threads_count_1000;

import java.util.stream.IntStream;

// as a task to use for threads;
// implementing Runnable interface
public class TaskSumNumbers implements Runnable {

    private static final int INITIAL_VALUE_RESULT_NUMBER = 0;
    private final int fromNumber;
    private final int toNumber;
    private int sum;
    private static final String TEMPLATE_MESSAGE_THREAD_NAME_AND_NUMBER = "%s : %d\n";

    public TaskSumNumbers(int fromNumber, int toNumber) {
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.sum = INITIAL_VALUE_RESULT_NUMBER;
    }

    @Override
    public void run() {
        //used rangeClosed, to get a correct result (include bounded numbers also)
        IntStream.rangeClosed(this.fromNumber, this.toNumber).forEach(i -> this.sum += i); //task to sum each number and return result as total sum result
        printThreadNameAndNumber(this.sum);
    }

    //getter to get a sum result:
    public int getSum() {
        return this.sum;
    }

    //helper method to kindly print thread name and number
    private static void printThreadNameAndNumber(int resultNumber) {
        System.out.printf(TEMPLATE_MESSAGE_THREAD_NAME_AND_NUMBER, Thread.currentThread().getName(), resultNumber);
    }

}
