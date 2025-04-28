package com.vladproduction.ch04_states_of_thread;

public class Runner_RUNNABLE {

    private static final String THREAD_STATE_CONSTANT = "%s : %s\n";

    public static void main(String[] args) {
        Thread thread = new Thread(() -> showThreadState(Thread.currentThread()));
        showThreadState(thread); //NEW
        thread.start();          //RUNNABLE
    }

    private static void showThreadState(Thread thread) {
        System.out.printf(THREAD_STATE_CONSTANT, thread.getName(), thread.getState());
    }

}
