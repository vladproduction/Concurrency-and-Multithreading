package com.vladproduction.ch16_lock_reentrant_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**A simple multithreaded design for generating even numbers while using a ReentrantLock to prevent race conditions
 * when multiple threads access shared data. Each thread operates independently, generating even numbers in a thread-safe manner.
 * An example to show how lock() is working;
 */
public class EvenNumberGeneratorLock {
    public static void main(String[] args) {

        EvenNumberGenerator evenNumberGenerator = new EvenNumberGenerator();

        Runnable generatingTask = () -> IntStream.range(0, 100).forEach(i -> System.out.println(evenNumberGenerator.generate()));

        Thread thread1 = new Thread(generatingTask);
        thread1.start();
        Thread thread2 = new Thread(generatingTask);
        thread2.start();
        Thread thread3 = new Thread(generatingTask);
        thread3.start();
    }

    private static final class EvenNumberGenerator{

        private final Lock lock;

        private int previousGenerated;

        public EvenNumberGenerator() {
            this.lock = new ReentrantLock();
            this.previousGenerated = -2;
        }

        public int generate(){
            lock.lock();
            try{
                return previousGenerated += 2;
            }finally {
                lock.unlock();
            }
        }


    }

}
