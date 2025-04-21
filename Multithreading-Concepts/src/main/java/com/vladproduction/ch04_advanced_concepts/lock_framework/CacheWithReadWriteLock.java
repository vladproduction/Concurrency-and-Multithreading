package com.vladproduction.ch04_advanced_concepts.lock_framework;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheWithReadWriteLock {

    private final Map<String, String> cache = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    // Multiple readers can execute this method simultaneously
    public String get(String key) {
        lock.readLock().lock();
        try {
            return cache.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    // Only one writer can execute at a time, blocks all readers
    public void put(String key, String value) {
        lock.writeLock().lock();
        try {
            cache.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    // Upgrade from read lock to write lock
    public String putIfAbsent(String key, String value) {
        lock.readLock().lock();
        try {
            String existingValue = cache.get(key);
            if (existingValue == null) {
                // Must release read lock before acquiring write lock
                lock.readLock().unlock();
                lock.writeLock().lock();
                try {
                    // Check again in case another thread set the value
                    existingValue = cache.get(key);
                    if (existingValue == null) {
                        cache.put(key, value);
                        return value;
                    }
                    return existingValue;
                } finally {
                    // Downgrade by acquiring read lock before releasing write lock
                    lock.readLock().lock();
                    lock.writeLock().unlock();
                }
            }
            return existingValue;
        } finally {
            lock.readLock().unlock();
        }
    }

}
