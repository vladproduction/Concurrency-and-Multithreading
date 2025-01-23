package com.vladproduction.priority_of_threads;

import static java.lang.System.*;
import static java.lang.Thread.*;

public class Main01 {
    private static final String MESSAGE_TEMPLATE_THREAD_NAME = "%s : %d\n"; //name:priority

    public static void main(String[] args) {
        //currentThread().setPriority(11); //IllegalArgumentException
        currentThread().setPriority(MAX_PRIORITY);

        Thread thread = new Thread(()->printNameAndPriority(currentThread()));
        thread.start();

        printNameAndPriority(currentThread());
    }

    private static void printNameAndPriority(Thread thread){
        out.printf(MESSAGE_TEMPLATE_THREAD_NAME, thread.getName(), thread.getPriority());
    }
}
