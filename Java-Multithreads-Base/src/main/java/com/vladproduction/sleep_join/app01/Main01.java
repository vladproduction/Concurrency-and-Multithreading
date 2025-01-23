package com.vladproduction.sleep_join.app01;

import java.util.stream.IntStream;

public class Main01 {

    private static final int FROM_NUMBER_THREAD_FIRST = 1;
    private static final int TO_NUMBER_THREAD_FIRST = 500;
    private static final int FROM_NUMBER_THREAD_SECOND = 501;
    private static final int TO_NUMBER_THREAD_SECOND = 1000;

    private static final String TEMPLATE_MESSAGE_THREAD_NAME_AND_NUMBER = "%s : %d\n";

    //private static final int MAIN_THREAD_DILAY_SECONDS = 1000;

    public static void main(String[] args) {

        TaskSummingNumbers firstTask = new TaskSummingNumbers(FROM_NUMBER_THREAD_FIRST, TO_NUMBER_THREAD_FIRST);
        //startThread(firstTask);
        Thread firstThread = new Thread(firstTask);
        firstThread.start();

        TaskSummingNumbers secondTask = new TaskSummingNumbers(FROM_NUMBER_THREAD_SECOND, TO_NUMBER_THREAD_SECOND);
        //startThread(secondTask);
        Thread secondThread = new Thread(secondTask);
        secondThread.start();

//        TaskSummingNumbers firstTask = startSubTask(FROM_NUMBER_THREAD_FIRST, TO_NUMBER_THREAD_FIRST);
//        TaskSummingNumbers secondTask = startSubTask(FROM_NUMBER_THREAD_SECOND, TO_NUMBER_THREAD_SECOND);
        waitForTasksFinished(firstThread, secondThread);
        int resultNumber = firstTask.getResultNumber() + secondTask.getResultNumber();
        printThreadNameAndNumber(resultNumber);

    }

//    private static TaskSummingNumbers startSubTask(int fromNumber, int toNumber){
//        TaskSummingNumbers subTask = new TaskSummingNumbers(fromNumber, toNumber);
//        Thread thread = new Thread(subTask);
//        thread.start();
//        return subTask;
//    }

    private static void printThreadNameAndNumber(int number){
        System.out.printf(TEMPLATE_MESSAGE_THREAD_NAME_AND_NUMBER,
                Thread.currentThread().getName(),number);
    }

    private static void waitForTasksFinished(Thread... threads){
        try {
            //Thread.sleep(MAIN_THREAD_DILAY_SECONDS);
            for (Thread t: threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    private static void startThread(Runnable runnable){
//        Thread thread = new Thread(runnable);
//        thread.start();
//
//    }

    private static final class TaskSummingNumbers implements Runnable{

        private static final int INITIAL_VALUE_RESULT_NUMBER = 0;
        private final int fromNumber;
        private final int toNumber;
        private int resultNumber;

        public TaskSummingNumbers(int fromNumber, int toNumber){
            this.fromNumber = fromNumber;
            this.toNumber = toNumber;
            this.resultNumber = INITIAL_VALUE_RESULT_NUMBER;
        }

        public int getResultNumber() {
            return this.resultNumber;
        }

        @Override
        public void run() {
            IntStream.rangeClosed(this.fromNumber, this.toNumber).forEach(i->this.resultNumber += i);
            printThreadNameAndNumber(this.resultNumber);
        }
    }
}
