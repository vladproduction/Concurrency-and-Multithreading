
package com.vladproduction.priority_of_threads;

import java.util.stream.IntStream;

import static java.lang.System.out;
import static java.lang.Thread.MAX_PRIORITY;
import static java.lang.Thread.currentThread;
import static java.util.stream.IntStream.*;

public class Main02 {
    private static final String MESSAGE_ABOUT_MAIN_THREAD_FINISH = "\tMAIN THREAD FINISHED";

    public static void main(String[] args) {

        Thread thread = new Thread(new Task());
        thread.setPriority(MAX_PRIORITY);
        thread.start();

        out.println(MESSAGE_ABOUT_MAIN_THREAD_FINISH);

    }

    private static final class Task implements Runnable{
        private static final int RANGE_MINIMAL_BORDER = 0;
        private static final int RANGE_MAXIMAL_BORDER = 100;

        @Override
        public void run() {
            range(RANGE_MINIMAL_BORDER,RANGE_MAXIMAL_BORDER).forEach(out::println);
        }
    }
}
