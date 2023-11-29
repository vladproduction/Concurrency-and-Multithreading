package com.vladproduction.lection_example;

public class Main {
    public static void main(String[] args) {
        ThreadPoolAndQueue threadPoolAndQueue = new ThreadPoolAndQueue();
        try {
            threadPoolAndQueue.threadAndQueue();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
