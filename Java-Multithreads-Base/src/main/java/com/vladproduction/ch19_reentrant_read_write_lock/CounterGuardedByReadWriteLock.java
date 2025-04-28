package com.vladproduction.ch19_reentrant_read_write_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CounterGuardedByReadWriteLock extends AbstractCounter{

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Lock readLock = readWriteLock.readLock();

    private final Lock writeLock = readWriteLock.writeLock();

    @Override
    protected Lock getReadLock() {
        return readLock;
    }

    @Override
    protected Lock getwriteLock() {
        return writeLock;
    }
}
