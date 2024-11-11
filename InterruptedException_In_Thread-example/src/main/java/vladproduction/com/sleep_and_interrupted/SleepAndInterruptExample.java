package vladproduction.com.sleep_and_interrupted;

/**
 * code correctly demonstrates the intended functionality of interrupting a sleeping thread:
 *
 * create a worker thread,
 * let it sleep,
 * and then interrupt it from the main thread.
 * */
public class SleepAndInterruptExample {
    public static void main(String[] args) {

        // 1) Create a new thread (the worker thread) and immediately start it.
        // The start() method begins the execution of the thread, which will run the code defined in the run().
        Thread worker = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " is going to sleep (10 sec)."); // Step 4
                // Thread goes to sleep for 10 seconds
                Thread.sleep(10000);
                System.out.println(Thread.currentThread().getName() + " woke up after being slept (10 sec)."); // Step 5 (not printed because was interrupted)
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted during sleep."); // Step 6 (printing because exception is throw while interrupted by main)
            }
        });

        // Set name for worker thread and start
        worker.setName("Worker");
        worker.start();

        // 2) The main thread sleeps for 5 seconds to give the worker thread time to start and begin its sleep phase.
        // It uses Thread.sleep(5000) to pause its execution. If the main thread is interrupted during this sleep, it catches the InterruptedException.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted while sleeping.");
        }

        // 3) Interrupt the worker thread
        System.out.println("Main thread is interrupting the Worker thread."); // Step 3
        worker.interrupt(); // This interrupts the running worker thread

        //output:
        /*  Worker is going to sleep (10 sec).
            Main thread is interrupting the Worker thread.
            Worker was interrupted during sleep.*/

    }
}





















