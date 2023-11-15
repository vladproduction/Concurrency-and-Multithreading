package vladproduction.com.create_and_start;

import static java.lang.System.*;
import static java.lang.Thread.*;
import static java.util.stream.IntStream.range;

public class Main04 {
    private static final int CREATED_THREADS_AMOUNT = 7;

    public static void main(String[] args) {

        Runnable displayingThreadName = () -> out.println(currentThread().getName());
        Runnable creatingThreads = () -> range(0,CREATED_THREADS_AMOUNT)
                .forEach(i->startThreads(displayingThreadName));

        startThreads(creatingThreads);
    }

    private static void startThreads(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
