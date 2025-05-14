package com.vladproduction.lambda.predicate;

import java.util.ArrayList;
import java.util.List;

public class PredicateListExample {
    public static void main(String[] args) {

        List<String> animals = new ArrayList<>();
        animals.add("Dog");
        animals.add("Cat");
        animals.add("Cow");
        animals.add("Ant");
        animals.add("Lion");

        System.out.println("animals: " + animals);

        animals.removeIf(animal -> animal.equals("Ant"));

        System.out.println("Last version animals: " + animals);



    }
}
