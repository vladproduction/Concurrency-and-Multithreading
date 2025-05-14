package com.vladproduction.lock_advanced_synchronization.reentrant_read_write_lock;

public class TestAppMain {
    public static void main(String[] args) {
        ReadWriteLockExample readWriteLockExample = new ReadWriteLockExample();

        Thread writerThread = new Thread(()-> readWriteLockExample.writeData(49), "Writer");

        Thread readerThread1 = new Thread(readWriteLockExample::readData, "Reader 1");
        Thread readerThread2 = new Thread(readWriteLockExample::readData, "Reader 2");

        writerThread.start();
        readerThread1.start();
        readerThread2.start();

        try{
            writerThread.join();
            readerThread1.join();
            readerThread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
