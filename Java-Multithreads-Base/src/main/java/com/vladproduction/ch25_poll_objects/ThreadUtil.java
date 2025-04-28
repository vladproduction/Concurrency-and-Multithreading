package com.vladproduction.ch25_poll_objects;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ThreadUtil {

    //create threads
    public static Thread[] createThread(Runnable task, int amountOfThreads){
        return IntStream.range(0, amountOfThreads)
                .mapToObj(i -> new Thread(task))
                .toArray(Thread[]::new);
    }

    //start threads
    public static void startThreads(Thread[] threads){
        Arrays.stream(threads).forEach(Thread::start);
    }

    private ThreadUtil(){
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
