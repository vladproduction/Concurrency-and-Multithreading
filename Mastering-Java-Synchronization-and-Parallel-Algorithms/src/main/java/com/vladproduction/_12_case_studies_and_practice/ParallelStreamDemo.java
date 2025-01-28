package com.vladproduction._12_case_studies_and_practice;

import java.util.Arrays;
import java.util.List;

public class ParallelStreamDemo {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Using parallel stream to compute the sum of squares
        long startTime = System.currentTimeMillis();
        
        int sumOfSquares = numbers.parallelStream()
                                   .mapToInt(num -> num * num) // Square of each number
                                   .sum(); // Sum the squares
        
        long duration = System.currentTimeMillis() - startTime;

        // Print the result and the time taken
        System.out.println("Sum of squares: " + sumOfSquares);
        System.out.println("Time taken (parallel stream): " + duration + " ms");
    }
}
