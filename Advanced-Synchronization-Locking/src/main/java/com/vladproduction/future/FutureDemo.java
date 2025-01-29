package com.vladproduction.future;

import java.util.concurrent.*;

public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("Main thread started");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future result = executor.submit(new Task());
        System.out.println("Main thread can do some more job and waiting for result(Future) to finish");
        System.out.println("result from Future is completed: " + result.get());
        System.out.println("Main thread finished");
        executor.shutdown();

        /* steps of this program:
        1-Main thread started
        2-Main thread can do some more job and waiting for result(Future) to finish
        3-Task started
        4-Task finished
        5-result from Future is completed: 27
        6-Main thread finished*/


    }
}

class Task implements Callable {

    @Override
    public Integer call() throws Exception {
        System.out.println("Task started");
        Thread.sleep(5000); //simulating work
        System.out.println("Task finished");
        return 27; //hardcoded to simplify return value
    }
}
