
package com.vladproduction.ch07_priority_of_threads;

import static java.lang.System.out;
import static java.lang.Thread.MAX_PRIORITY;
import static java.util.stream.IntStream.*;

public class MainThreadExecuteFirst {
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
            range(RANGE_MINIMAL_BORDER,RANGE_MAXIMAL_BORDER).forEach(out::print);
        }
    }

    /**
     * Even though the child thread is set to MAX_PRIORITY (which is 10), the main thread still executes first in this code.
     * There are several important reasons for this behavior:
     *
     * Thread Scheduling Is Not Deterministic:
     *
     * Java thread priorities are merely hints to the underlying operating system's scheduler
     * The JVM delegates scheduling decisions to the OS, which may have its own priority system
     * High priority doesn't guarantee immediate execution
     *
     *
     * Thread Creation Overhead:
     *
     * There's a small but significant overhead in creating and starting a new thread
     * By the time the child thread is fully initialized and ready to run, the main thread has already executed its simple print statement
     *
     *
     * Code Execution Flow:
     *
     * The main thread executes these operations sequentially:
     *
     * Creates the thread object
     * Sets its priority
     * Starts the thread
     * Prints the message
     *
     *
     * The print statement executes before the child thread's task has a chance to start
     *
     *
     * CPU Cores and Context Switching:
     *
     * Even on multi-core systems, there might be a small delay before the OS allocates CPU time to the new thread
     * The main thread's print operation is extremely fast compared to the context switch
     *
     *
     * Thread Scheduling Granularity:
     *
     * The scheduler doesn't reassign CPU resources for very short operations
     * The message printing is completed before the next scheduling decision
     * */

}
