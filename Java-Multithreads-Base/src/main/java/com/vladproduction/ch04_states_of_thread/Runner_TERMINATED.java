package com.vladproduction.ch04_states_of_thread;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;

public class Runner_TERMINATED {

    private static final String MESSAGE_TEMPLATE_THREAD_STATE = "%s : %s\n";

    public static void main(String[] args) throws InterruptedException{
        Thread thread = new Thread(()->showThreadState(currentThread())); //RUNNABLE
        thread.start();
        thread.join();
        showThreadState(thread); //TERMINATED
    }

    private static void showThreadState(Thread thread){
        out.printf(MESSAGE_TEMPLATE_THREAD_STATE, thread.getName(), thread.getState());
    }

}
