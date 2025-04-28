package com.vladproduction.ch10_unhandled_exception_handlers;

public class GlobalSetUncaughtExceptionHandlerDemo {

    public static final String MESSAGE_EXCEPTION_TEMPLATE = "Exception was thrown with message '%s' in thread '%s'.\n";

    public static void main(String[] args) {

        //define handler:
        Thread.UncaughtExceptionHandler handler = (thread, exception) -> {
            System.out.printf(MESSAGE_EXCEPTION_TEMPLATE, exception.getMessage(), thread.getName());
        };

        Thread.setDefaultUncaughtExceptionHandler(handler);

        //we do not need to set UncaughtExceptionHandler every time, all threads created now by default will handle it
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
