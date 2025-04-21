package com.vladproduction.ch04_advanced_concepts.memory_model;

import java.util.concurrent.atomic.AtomicInteger;

public class MemoryBarrierExample {

    private int a = 0;
    private volatile boolean flag = false;

    public void writer() {
        a = 42;              // Write to a
        flag = true;         // Write to flag with release semantics
    }

    public int reader() {
        if (flag) {          // Read of flag with acquire semantics
            return a;        // If flag is true, a is guaranteed to be 42
        }
        return 0;
    }

    // Demonstration of happens-before relationship
    private int x = 0, y = 0;
    private volatile boolean ready = false;

    public void thread1() {
        x = 1;               // Regular write
        y = 2;               // Regular write
        ready = true;        // Volatile write creates happens-before relationship
    }

    public void thread2() {
        if (ready) {         // Volatile read establishes happens-before relationship
            // If ready is true, x and y are guaranteed to be 1 and 2
            System.out.println("x: " + x + ", y: " + y);
        }
    }

    // Using AtomicInteger for memory ordering
    private AtomicInteger atomicVal = new AtomicInteger(0);
    private int nonAtomicVal = 0;

    public void atomicWriter() {
        nonAtomicVal = 100;          // Regular write
        atomicVal.set(1);            // Atomic write acts as a memory barrier
    }

    public void atomicReader() {
        if (atomicVal.get() == 1) {  // Atomic read acts as a memory barrier
            // If atomicVal is 1, nonAtomicVal is guaranteed to be 100
            System.out.println("nonAtomicVal: " + nonAtomicVal);
        }
    }

    // Using final fields for initialization safety
    private final class ImmutablePoint {
        private final int x;
        private final int y;

        public ImmutablePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() { return x; }
        public int getY() { return y; }
    }

    // Demonstration of double-checked locking with volatile
    private volatile static MemoryBarrierExample instance;

    public static MemoryBarrierExample getInstance() {
        if (instance == null) {           // First check (no locking)
            synchronized (MemoryBarrierExample.class) {
                if (instance == null) {    // Second check (with locking)
                    instance = new MemoryBarrierExample();
                }
            }
        }
        return instance;
    }

}
