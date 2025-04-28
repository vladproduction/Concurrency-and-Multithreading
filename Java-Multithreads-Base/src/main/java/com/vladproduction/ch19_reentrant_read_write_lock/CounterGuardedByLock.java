package com.vladproduction.ch19_reentrant_read_write_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class CounterGuardedByLock extends AbstractCounter {

    private final Lock lock = new ReentrantLock();

    @Override
    protected Lock getReadLock() {
        return this.lock;
    }

    @Override
    protected Lock getwriteLock() {
        return this.lock;
    }
}
