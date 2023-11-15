package vladproduction.com.synchronized_key_word;

import java.util.function.IntConsumer;

import static java.lang.System.out;
import static java.util.stream.IntStream.range;

public class Main02 {

    public static int firstCounter = 0;
    public static int secondCounter = 0;
    public static final int INCREMENT_AMOUNT_FIRST_THREAD = 100_000;
    public static final int INCREMENT_AMOUNT_SECOND_THREAD = 100_000;


    public static void main(String[] args) throws InterruptedException {
        Thread firstThread = createIncrementingCounterThread(INCREMENT_AMOUNT_FIRST_THREAD,
                i-> incrementFirstCounter());
        Thread secondThread = createIncrementingCounterThread(INCREMENT_AMOUNT_SECOND_THREAD,
                i->incrementSecondCounter());

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();

        out.println(firstCounter);
        out.println(secondCounter);
    }

    private static Thread createIncrementingCounterThread(final int incrementAmount,
                                                          IntConsumer incrementingOperation){
        Thread thread = new Thread(()->{
            range(0, incrementAmount).forEach(incrementingOperation);
        });
        return thread;
    }

    private static  void incrementFirstCounter(){
        firstCounter++;
    }
    private static  void incrementSecondCounter(){
        secondCounter++;
    }
}
