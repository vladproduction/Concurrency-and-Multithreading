package com.vladproduction.lock_advanced_synchronization.reentrant_read_write_lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    private int data = 0;

    public void writeData(int newData){
        writeLock.lock();
        try {
            data = newData;
            System.out.println(Thread.currentThread().getName() + " wrote: " + data);
        }finally {
            writeLock.unlock();
        }
    }

    public void readData(){
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " read: " + data);
        }finally {
            readLock.unlock();
        }
    }

}
