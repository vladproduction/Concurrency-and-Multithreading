package com.vladproduction.ch23_atomic_classes;

import java.util.concurrent.atomic.AtomicInteger;

public final class EvenNumberGenerator {

    private static final int GENERATION_DELTA = 2;

    private final AtomicInteger value = new AtomicInteger();

    public void generate(){
        value.getAndAdd(GENERATION_DELTA); //atomic operation (increment is not, even if it is increment and get)
    }

    public int getValue(){
        return value.intValue();
    }


}
