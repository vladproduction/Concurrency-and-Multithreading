package com.vladproduction.stream_api;

import java.util.List;
import java.util.stream.Stream;

public class TypesStreamMethods {
    public static void main(String[] args) {

        //Terminal Operation Methods --> do not return a stream; after this stream is clos
        //Intermediate Operation Methods --> returns a stream

        List<String> animals = List.of("Dog", "Cat", "Cow", "Ant", "Lion");
        Stream<String> animalStream = animals.stream();
        animalStream.map(String::toUpperCase)
                .forEach(System.out::println); //terminal operation
//        animalStream.map(String::toLowerCase); //not allowed because the stream is already closed; IllegalStateException






    }
}
