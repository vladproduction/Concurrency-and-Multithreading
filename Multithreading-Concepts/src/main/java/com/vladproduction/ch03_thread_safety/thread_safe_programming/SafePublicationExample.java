package com.vladproduction.ch03_thread_safety.thread_safe_programming;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class SafePublicationExample {

    // Using volatile for safe publication
    private volatile ComplexObject instance;

    // Using final field for safe publication
    private final ComplexObject finalInstance = new ComplexObject();

    // Using concurrent collections
    private final ConcurrentHashMap<String, ComplexObject> cache =
            new ConcurrentHashMap<>();

    // Double-checked locking pattern with volatile
    public ComplexObject getInstance() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = new ComplexObject();
                }
            }
        }
        return instance;
    }

    // Using AtomicReference for safe publication
    private final AtomicReference<ComplexObject> atomicInstance =
            new AtomicReference<>();

    public void updateInstanceSafely(ComplexObject newObject) {
        atomicInstance.set(newObject);
    }

    // Using static initialization (JVM guarantees safe publication)
    private static class Holder {
        static final ComplexObject INSTANCE = new ComplexObject();
    }

    public ComplexObject getInstanceLazily() {
        return Holder.INSTANCE;
    }

    private static class ComplexObject { }

}
