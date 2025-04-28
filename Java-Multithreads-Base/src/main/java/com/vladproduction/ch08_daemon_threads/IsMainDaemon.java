package com.vladproduction.ch08_daemon_threads;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

public class IsMainDaemon {

    private static final String MESSAGE_MAIN_THREAD_IS_FINISHED = "Main thread is finished";

    public static void main(String[] args) {

        out.println("---main thread is not daemon--------");
        System.out.println(Thread.currentThread().isDaemon());

        out.println("---child thread created and set as daemon--------");
        Thread thread = new Thread(new Task());
        thread.setDaemon(true);
        thread.start();

        out.println("thread is Daemon = " + thread.isDaemon());
        out.println(MESSAGE_MAIN_THREAD_IS_FINISHED);
    }

    private static final class Task implements Runnable{

        private static final String MESSAGE_OF_APP_STATE = "\tAPP IS WORK";
        private static final int SLEEPING_BETWEEN_MESSAGES = 2;

        @Override
        public void run() {
            try{
                while (true){
                    out.println(MESSAGE_OF_APP_STATE);
                    SECONDS.sleep(SLEEPING_BETWEEN_MESSAGES);
                }
            }catch (InterruptedException interruptedException){
                currentThread().interrupt();
            }
        }
    }

}
