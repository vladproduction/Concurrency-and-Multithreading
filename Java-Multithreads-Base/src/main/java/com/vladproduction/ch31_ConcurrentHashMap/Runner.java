package com.vladproduction.ch31_ConcurrentHashMap;

public class Runner {
    public static void main(String[] args) {
        CounterTestUtil.test(new SingleThreadLetterCounter());
        CounterTestUtil.test(new MultiThreadLetterCounter(5));
    }
}
