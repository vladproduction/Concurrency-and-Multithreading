package com.vladproduction.ch30_synchronized_collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.IntStream;

public class ArrayListSynchronized {
    public static void main(String[] args) {


        //ArrayList is now thread safe because we use synchronized method from Collections
        List<Integer> values = Collections.synchronizedList(new ArrayList<>());

        Runnable task = () -> {
            IntStream.range(0, 1000).forEach(values::add);
        };
        Thread threadOne = new Thread(task);
        Thread threadTwo = new Thread(task);
        threadOne.start();
        threadTwo.start();

        try{
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("The list size is: " + values.size()); //expected: 2000

    }
}
