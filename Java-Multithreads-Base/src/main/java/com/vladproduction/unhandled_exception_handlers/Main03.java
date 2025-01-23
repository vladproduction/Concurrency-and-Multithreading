package com.vladproduction.unhandled_exception_handlers;

public class Main03 {
    public static final String MESSAGE_EXCEPTION_TEMPLATE =
            "Exception was thrown with message '%s' in thread '%s'.\n";
    public static void main(String[] args) {
        //define handler:
        Thread.UncaughtExceptionHandler handler = (thread, exception) -> {
            System.out.printf(MESSAGE_EXCEPTION_TEMPLATE, exception.getMessage(), thread.getName());
        };

        Thread.setDefaultUncaughtExceptionHandler(handler);

        Thread firstThread = new Thread(new Task());
        firstThread.start();

        Thread secondThread = new Thread(new Task());
        secondThread.start();


    }

    public static final class Task implements Runnable{
        public static final String EXCEPTION_MESSAGE = "EXCEPTION_MESSAGE";
        @Override
        public void run() {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }
}
