package com.vladproduction.ch04_states_of_thread;

public class Runner_TIMED_WAITING {

    private static final String THREAD_STATE_CONSTANT = "%s : %s\n";
    public static final int AMOUNT_TIME_SLEEPING_IN_MAIN_THREAD = 1000;
    public static final int AMOUNT_TIME_TO_JOIN_IN_THREAD_ON_MAIN_THREAD = 2000;

    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();
        Thread thread = new Thread(() -> {
            try{
                mainThread.join(AMOUNT_TIME_TO_JOIN_IN_THREAD_ON_MAIN_THREAD);
                showThreadState(Thread.currentThread());       //RUNNABLE
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(AMOUNT_TIME_SLEEPING_IN_MAIN_THREAD);
        showThreadState(thread);                                //TIMED_WAITING
    }

    private static void showThreadState(Thread thread) {
        System.out.printf(THREAD_STATE_CONSTANT, thread.getName(), thread.getState());
    }

}
