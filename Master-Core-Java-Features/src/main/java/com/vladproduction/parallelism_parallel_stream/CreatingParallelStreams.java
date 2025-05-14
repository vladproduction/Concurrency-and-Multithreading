package com.vladproduction.parallelism_parallel_stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CreatingParallelStreams {
    public static void main(String[] args) {

        // Method 1: From a collection using parallelStream()
        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        Stream<Integer> parallelNumberStream = numbers.parallelStream();

        // Method 2: Converting a sequential stream using parallel()
        Stream<Integer> parallelStream2 = numbers.stream().parallel();

    }
}
