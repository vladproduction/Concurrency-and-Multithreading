package com.vladproduction.java_collections.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorExample {
    public static void main(String[] args) {

        System.out.println("\n\n-------------Comparator Example - Character List-------------");
        // Create a list to hold Character objects
        List<Character> charsList = new ArrayList<>();
        charsList.add('b');
        charsList.add('Z');
        charsList.add('A');
        charsList.add('Z');
        charsList.add('d');
        charsList.add('F');

        // Sort the characters in the list using a custom comparator
        Collections.sort(charsList, new MyCharsComparator());
        System.out.println("Sorted charsList: " + charsList + " by MyCharsComparator in ASC order");

        System.out.println("\n\n------------- Comparator Example - Car List -------------");
        List<Cars> carsList = new ArrayList<>();
        carsList.add(new Cars("Volvo", 2010, 10000));
        carsList.add(new Cars("BMW", 2012, 25000));
        carsList.add(new Cars("Ford", 2015, 15000));
        carsList.add(new Cars("Mazda", 2018, 9000));
        carsList.add(new Cars("Citroen", 2019, 1000));

        Comparator<Cars> comparator = Comparator.comparing(Cars::getCarName);
        Collections.sort(carsList, comparator);
        System.out.printf("%-10s %-6s %-8s%n", "Car", "Year", "Price");
        System.out.println("---------------------------");
        for (Cars car : carsList) {
            System.out.println(car);
        }




    }

    // Custom comparator class for comparing Character objects
    private static final class MyCharsComparator implements Comparator<Character> {

        // Comparison logic:
        // - Returns a positive value if o1 > o2 (o1 is greater than o2)
        // - Returns a negative value if o1 < o2 (o1 is less than o2)
        // - Returns 0 if o1 == o2 (o1 is equal to o2)
        // ASC order: smallest to largest
        // Sorting is in ascending order based on the natural order of characters.

        @Override
        public int compare(Character o1, Character o2) {
            if(o1 > o2){
                return 1;
            } else if(o1 < o2){
                return -1;
            } else {
                return 0;
            }
        }
    }
}
