package com.vladproduction.ch07_priority_of_threads;

import static java.lang.Thread.*;

public class SettingPriority {

    private static final String TEMPLATE_THREAD_NAME = "%s : %d\n";

    public static void main(String[] args) {

        System.out.println("\n===default===");
        System.out.println("Main thread started  with default priority: 5");
        printNameAndPriority(currentThread());// default: 5

        System.out.println("\n===min===");
        currentThread().setPriority(Thread.MIN_PRIORITY); // setting: 1
        System.out.println("Main thread set with min priority: 1");
        printNameAndPriority(currentThread()); // now it stands with priority 1

        System.out.println("\n===max===");
        currentThread().setPriority(Thread.MAX_PRIORITY);
        System.out.println("Main thread set with max priority: 10");
        printNameAndPriority(currentThread());

        System.out.println("\n===sub thread have same priority as main thread===");
        Thread subThread = new Thread(() -> printNameAndPriority(currentThread()));
        subThread.start();

    }

    private static void printNameAndPriority(Thread thread){
        System.out.printf(TEMPLATE_THREAD_NAME, thread.getName(), thread.getPriority());
    }
}
