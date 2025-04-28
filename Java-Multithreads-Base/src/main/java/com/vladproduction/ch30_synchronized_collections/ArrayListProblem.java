package com.vladproduction.ch30_synchronized_collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.IntStream;

public class ArrayListProblem {
    public static void main(String[] args) {

        //Vector is thread safe
        //ArrayList is not thread safe
//        List<Integer> values = new ArrayList<>();
        List<Integer> values = new Vector<>();


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
