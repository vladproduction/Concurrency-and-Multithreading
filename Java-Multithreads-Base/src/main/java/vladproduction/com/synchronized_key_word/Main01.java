package vladproduction.com.synchronized_key_word;

import static java.lang.System.out;
import static java.util.stream.IntStream.range;

public class Main01 {

    public static int counter = 0;
    public static final int INCREMENT_AMOUNT_FIRST_THREAD = 100_000;
    public static final int INCREMENT_AMOUNT_SECOND_THREAD = 100_000;


    public static void main(String[] args) throws InterruptedException {
        Thread firstThread = createIncrementingCounterThread(INCREMENT_AMOUNT_FIRST_THREAD);
        Thread secondThread = createIncrementingCounterThread(INCREMENT_AMOUNT_SECOND_THREAD);

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();

        out.println(counter);
    }

    private static Thread createIncrementingCounterThread(final int incrementAmount){
        Thread thread = new Thread(()->{
            range(0, incrementAmount).forEach(i->incrementCounter());
        });
        return thread;
    }

    private static synchronized void incrementCounter(){
        counter++;
    }
}
