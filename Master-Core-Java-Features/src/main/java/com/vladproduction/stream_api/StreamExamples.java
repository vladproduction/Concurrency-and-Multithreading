package com.vladproduction.stream_api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamExamples {
    public static void main(String[] args) {

        System.out.println("-----create stream via Stream Interface and print elements----");
        List<Integer> numbers = Arrays.asList(1, 4, 10, 3, 6, 1, 15, 4, 9, 16, 5);
        Stream<Integer> numStream = numbers.stream();
        numStream.forEach(n -> System.out.println(n));
//        numStream.forEach(System.out::println);

        System.out.println("\n-----create stream and print elements----");
        numbers.stream().forEach(System.out::println);
//        numbers.forEach(System.out::println);

        System.out.println("\n-----filter() method example----");
        //print even numbers:
        numbers.stream()
                .filter(n -> n % 2 == 0) // intermediate operation
                .forEach(System.out::println); // termination operation

        System.out.println("\n-----map() method example----");
        //map() method: (we filtering every even nums and add 1, then we print)
        numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n + 1)
                .forEach(System.out::println);

        System.out.println("\n-----sorted() method example----");
        //sorted() method: (in natural order)
        numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n + 1)
                .sorted()                               //stateful intermediate operation
                .forEach(System.out::println);

        System.out.println("\n-----sorted() and distinct() method example----");
        //sorted() method: (in natural order and print only unique)
        numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n + 1)
                .sorted()                               //stateful intermediate operation
                .distinct()                             //stateful intermediate operation also
                .forEach(System.out::println);



    }
}
