package com.vladproduction.state;

import static java.lang.String.format;
import static java.lang.System.out;
import static java.lang.Thread.currentThread;

public class Main05 {

    private static final String MESSAGE_TEMPLATE_THREAD_STATE = "%s : %s\n";


    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            throw new RuntimeException();
        });
        thread.start();
        thread.join();
        showThreadState(thread);


    }

    private static void showThreadState(Thread thread) {
        out.println(format(MESSAGE_TEMPLATE_THREAD_STATE, thread.getName(), thread.getState()));

    }
}
