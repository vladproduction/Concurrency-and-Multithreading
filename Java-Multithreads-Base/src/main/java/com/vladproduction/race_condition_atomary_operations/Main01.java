package com.vladproduction.race_condition_atomary_operations;

import java.util.stream.IntStream;

import static java.lang.System.*;
import static java.util.stream.IntStream.*;

public class Main01 {

    public static int counter = 0;
    public static final int INCREMENT_AMOUNT_FIRST_THREAD = 100_001;
    public static final int INCREMENT_AMOUNT_SECOND_THREAD = 100_001;


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
            range(0, incrementAmount).forEach(i->counter++);
        });
        return thread;
    }
}
