package vladproduction.com.daemon;

import static java.lang.System.out;
import static java.lang.Thread.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main05 {

    private static final int SLEEP_DURATION_IN_MAIN = 1;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Task());
        thread.setDaemon(true);
        thread.start();

        SECONDS.sleep(SLEEP_DURATION_IN_MAIN);

    }

    private static final class Task implements Runnable{
        private static final String MESSAGE_THAT_TASK_STARTED = "Execution of task started!";
        private static final String MESSAGE_THAT_TASK_FINISHED = "\tExecution of task finished!!!";
        private static final int SLEEP_BETWEEN_START_AND_FINISH = 5;
        @Override
        public void run() {
            try{
                out.println(MESSAGE_THAT_TASK_STARTED);
                SECONDS.sleep(SLEEP_BETWEEN_START_AND_FINISH);
            }
            catch (InterruptedException interruptedException){
                currentThread().interrupt();
            }
            finally {
                out.println(MESSAGE_THAT_TASK_FINISHED);
            }
        }
    }
}
