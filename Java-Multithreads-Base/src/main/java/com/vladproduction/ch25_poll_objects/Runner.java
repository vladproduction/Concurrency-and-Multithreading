package com.vladproduction.ch25_poll_objects;

public class Runner {
    public static void main(String[] args) {

        int poolSize = 3;
        ConnectionPool pool = new ConnectionPool(poolSize);

        ConnectionPoolTask poolTask = new ConnectionPoolTask(pool);
        int threadCount = 15;

        Thread[] threads = ThreadUtil.createThread(poolTask, threadCount);
        ThreadUtil.startThreads(threads);

    }
}
