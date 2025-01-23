package com.vladproduction.threads_factory;

import java.util.concurrent.ThreadFactory;

import static java.lang.System.out;
import static java.lang.Thread.*;

public class Main01 {
    public static final String MESSAGE_EXCEPTION_TEMPLATE =
            "Exception was thrown with message '%s' in thread '%s'.\n";
    public static void main(String[] args) throws InterruptedException {
        //define handler:
        UncaughtExceptionHandler handler = (thread, exception) -> {
            out.printf(MESSAGE_EXCEPTION_TEMPLATE, exception.getMessage(), thread.getName());
        };

        //define factory:
        ThreadFactory threadFactory = new DaemonThreadWithUncaughtExceptionHandlerFactory(handler);

        Thread firstThread = threadFactory.newThread(new Task());
        firstThread.start();

        Thread secondThread = threadFactory.newThread(new Task());
        secondThread.start();

        firstThread.join();
        secondThread.join();


    }

    public static final class Task implements Runnable{
        public static final String EXCEPTION_MESSAGE = "EXCEPTION_MESSAGE";
        @Override
        public void run() {
            out.println(currentThread().isDaemon());
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }
    public static final class DaemonThreadWithUncaughtExceptionHandlerFactory implements ThreadFactory {

        private final UncaughtExceptionHandler uncaughtExceptionHandler;
        public DaemonThreadWithUncaughtExceptionHandlerFactory(UncaughtExceptionHandler uncaughtExceptionHandler) {
            this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        }
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            thread.setDaemon(true);
            return thread;
        }
    }

}
