package com.vladproduction.ch19_reentrant_read_write_lock;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class MainApp {
    public static void main(String[] args) throws InterruptedException {

        //version #1 (no sleeping at getValue() method)
//        testCounter(CounterGuardedByLock::new);
//        testCounter(CounterGuardedByReadWriteLock::new);

        //version #2 (sleeping at getValue() method for 1 second)
//        testCounter(CounterGuardedByLock::new);
        testCounter(CounterGuardedByReadWriteLock::new);

        /**
        version #1
        CounterGuardedByLock            reads: 195313544
        CounterGuardedByReadWriteLock   reads: 21052196

        version #2
        CounterGuardedByLock            reads: 54
        CounterGuardedByReadWriteLock   reads: 179

         * conclusion: test first and then choose one which is more effective.
         *  - how often we read data or/and how often we increment counter;
         *  - time taken to read data, etc.;
         * */

    }

    private static void testCounter(final Supplier<? extends AbstractCounter> counterFactory) throws InterruptedException {

        final AbstractCounter counter = counterFactory.get();

        int amountOfThreadsGettingValue = 50;
        ReadingValueTask[] readingValueTasks = createReadingTasks(counter, amountOfThreadsGettingValue);
        Thread[] readingValueThreads = mapToThreads(readingValueTasks);

        Runnable incrementingCounterTask = createIncrementingCounterTask(counter);
        int amountOfThreadsIncrementingCounter = 2;
        Thread[] incrementingCounterThreads = createThreads(incrementingCounterTask, amountOfThreadsIncrementingCounter);

        startThreads(readingValueThreads);
        startThreads(incrementingCounterThreads);

        TimeUnit.SECONDS.sleep(5);

        interruptThreads(readingValueThreads);
        interruptThreads(incrementingCounterThreads);

        waitUntilFinish(readingValueThreads);

        long totalAmountOfReads = findTotalAmountOfReads(readingValueTasks);
        System.out.printf("Total amount of reads: %d\n", totalAmountOfReads);

    }

    private static ReadingValueTask[] createReadingTasks(AbstractCounter counter, int numberOfTasks){
        return IntStream.range(0, numberOfTasks).mapToObj(i -> new ReadingValueTask(counter))
                .toArray(ReadingValueTask[]::new);
    }

    private static Thread[] mapToThreads(Runnable[] tasks){
        return Arrays.stream(tasks).map(Thread::new).toArray(Thread[]::new);
    }

    private static Runnable createIncrementingCounterTask(AbstractCounter counter){
        return () -> {
            while (!Thread.currentThread().isInterrupted()){
                incrementCounter(counter);
            }
        };
    }

    private static void incrementCounter(AbstractCounter counter){
        try{
            counter.increment();
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    private static Thread[] createThreads(Runnable task, int numberOfThreads){
        return IntStream.range(0, numberOfThreads).mapToObj(i -> new Thread(task)).toArray(Thread[]::new);
    }

    private static void forEach(Thread[] threads, Consumer<Thread> action){
        Arrays.stream(threads).forEach(action);
    }

    private static void startThreads(Thread[] threads){
        forEach(threads, Thread::start);
    }

    private static void interruptThreads(Thread[] threads){
        forEach(threads, Thread::interrupt);
    }

    private static void waitUntilFinish(Thread thread) {
        try{
            thread.join();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    private static void waitUntilFinish(Thread[] threads) {
        forEach(threads, MainApp::waitUntilFinish);
    }

    private static long findTotalAmountOfReads(ReadingValueTask[] readingValueTasks){
        return Arrays.stream(readingValueTasks).mapToLong(ReadingValueTask::getAmountOfReads).sum();
    }

    private static final class ReadingValueTask implements Runnable{

        private final AbstractCounter counter; //counter from where we're going to read data
        private long amountOfReads; //an amount of reading operations

        public ReadingValueTask(AbstractCounter counter){
            this.counter = counter;
        }

        public long getAmountOfReads() {
            return amountOfReads;
        }

        //while thread is not interrupted, read value from counter,
        //and increase amountOfReads by 1, so we can know how many times we read value
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                counter.getValue();
                amountOfReads++;
            }
        }
    }

}
