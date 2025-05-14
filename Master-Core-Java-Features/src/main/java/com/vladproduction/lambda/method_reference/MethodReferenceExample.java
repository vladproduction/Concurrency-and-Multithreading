package com.vladproduction.lambda.method_reference;

import java.util.List;

public class MethodReferenceExample {
    public static void main(String[] args) {

        List<String> animals = List.of("Dog", "Cat", "Cow", "Ant", "Lion");

        //Consumer Interface and lambda:
//        Consumer<String> animalConsumer = animal -> System.out.println(animal);
//        animals.forEach(animalConsumer);

        //consumer by method reference:
//        Consumer<String> animalConsumer = System.out::println;
//        animals.forEach(animalConsumer);

        //lambda:
//        animals.forEach(a -> System.out.println(a));

        //method reference:
        animals.forEach(System.out::println);

    }
}
