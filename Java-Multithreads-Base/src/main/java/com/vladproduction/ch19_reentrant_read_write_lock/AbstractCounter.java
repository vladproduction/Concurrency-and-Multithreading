package com.vladproduction.ch19_reentrant_read_write_lock;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public abstract class AbstractCounter {

    private long value;

    //since we have to read data, we need to use read lock
    public final OptionalLong getValue() {
        Lock lock = getReadLock();
        lock.lock();
        try{
            TimeUnit.SECONDS.sleep(1);
            return OptionalLong.of(this.value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return OptionalLong.empty();
        } finally{
            lock.unlock();
        }
    }

    //since we have to write data, we need to use write lock for modification data
    public final void increment() {
        Lock lock = getwriteLock();
        lock.lock();
        try{
            this.value++;
        }finally {
            lock.unlock();
        }
    }

    protected abstract Lock getReadLock();
    protected abstract Lock getwriteLock();
}
