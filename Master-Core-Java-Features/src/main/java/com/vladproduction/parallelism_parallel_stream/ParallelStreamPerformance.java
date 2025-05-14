package com.vladproduction.parallelism_parallel_stream;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

public class ParallelStreamPerformance {
    public static void main(String[] args) {

        // Size of the range to sum
        long n = 100_000_000;

        // Sequential stream
        Instant start = Instant.now();
        long sequentialSum = LongStream.rangeClosed(1, n).sum();
        Instant end = Instant.now();
        long sequentialTime = Duration.between(start, end).toMillis();

        //Parallel stream
        start = Instant.now();
        long parallelSum = LongStream.rangeClosed(1, n).parallel().sum();
        end = Instant.now();
        long parallelTime = Duration.between(start, end).toMillis();

        //printing performances in millis:
        System.out.println("Sequential sum: " + sequentialSum + " in " + sequentialTime + " ms");
        System.out.println("Parallel sum: " + parallelSum + " in " + parallelTime + " ms");

    }
}
