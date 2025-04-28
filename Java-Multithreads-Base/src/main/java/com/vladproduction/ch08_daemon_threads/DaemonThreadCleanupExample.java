package com.vladproduction.ch08_daemon_threads;


import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Demonstrates that daemon threads may not execute their cleanup code (finally blocks)
 * when the JVM terminates after all non-daemon threads complete.
 *
 * This is a critical consideration when using daemon threads for tasks that require
 * proper resource cleanup or final state updates.
 */
public class DaemonThreadCleanupExample {

    // Main thread will only run for this duration before exiting
    private static final int MAIN_THREAD_LIFESPAN_SECONDS = 1;

    public static void main(String[] args) throws InterruptedException {
        out.println("Main thread started (non-daemon)");

        // Create and start a daemon worker thread
        Thread workerThread = new Thread(new CleanupWorker());
        workerThread.setDaemon(true);
        workerThread.start();

        // Main thread sleeps briefly
        out.println("Main thread sleeping for " + MAIN_THREAD_LIFESPAN_SECONDS + " second");
        SECONDS.sleep(MAIN_THREAD_LIFESPAN_SECONDS);

        out.println("Main thread exiting - JVM will terminate and kill daemon threads");
        // No explicit exit call needed - once main() completes, all daemon threads will be terminated
    }

    /**
     * Worker task that attempts to perform cleanup in its finally block.
     * When run as a daemon thread, this cleanup may never execute if the JVM
     * terminates before the finally block is reached.
     */
    private static final class CleanupWorker implements Runnable {
        private static final String STARTUP_MESSAGE = "Worker started: Beginning task execution";
        private static final String CLEANUP_MESSAGE = "Worker cleanup: Releasing resources in finally block";
        private static final int WORK_DURATION_SECONDS = 5;

        @Override
        public void run() {
            try {
                out.println(STARTUP_MESSAGE);

                // Simulate doing some work that takes time
                out.println("Worker processing - will take " + WORK_DURATION_SECONDS + " seconds");
                SECONDS.sleep(WORK_DURATION_SECONDS);

                out.println("Worker task completed successfully");
            }
            catch (InterruptedException e) {
                out.println("Worker was interrupted");
                currentThread().interrupt();
            }
            finally {
                // IMPORTANT: This finally block may never execute if the thread is a daemon
                // and the JVM terminates before this point is reached
                out.println(CLEANUP_MESSAGE);

                // Examples of what might be in a real cleanup block:
                // - Closing file handles
                // - Releasing database connections
                // - Flushing buffers
                // - Releasing locks
            }
        }
    }

}
