package com.vladproduction.thread_pools;

import java.util.List;

public class ParallelStreamWithPoolExample {
    public static void main(String[] args) {

        List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10);
        numbers.parallelStream().forEach(number->{
            System.out.println("Thread: " + Thread.currentThread().getName() + ", Number: " + number + ", Squared: " + (number * number));
        });

    }
}
