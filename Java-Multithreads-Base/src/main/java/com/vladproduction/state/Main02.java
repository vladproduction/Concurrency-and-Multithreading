package com.vladproduction.state;

import static java.lang.String.format;
import static java.lang.System.out;
import static java.lang.Thread.*;
import static java.lang.Thread.currentThread;

public class Main02 {

    private static final String MESSAGE_TEMPLATE_THREAD_STATE = "%s : %s\n";
    private static final int SLEEPING_IN_MAIN_THREAD = 3;

    public static void main(String[] args) throws InterruptedException{
        Thread mainThread = currentThread();
        Thread thread = new Thread(()->{
            try{
                mainThread.join();
                showThreadState(currentThread());
            }catch (InterruptedException interruptedException){

            }
        });
        thread.start();
        sleep(SLEEPING_IN_MAIN_THREAD);
        showThreadState(thread);
    }

    private static void showThreadState(Thread thread){
        out.println(format(MESSAGE_TEMPLATE_THREAD_STATE, thread.getName(), thread.getState()));

    }
}
