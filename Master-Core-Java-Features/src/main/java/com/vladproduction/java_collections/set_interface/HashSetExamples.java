package com.vladproduction.java_collections.set_interface;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class HashSetExamples {
    public static void main(String[] args) {

        //HashSet is a non-ordered collection of unique elements.
        System.out.println("*********** HashSet ***********");
        Set<String> cars = new HashSet<>();
        cars.add("Mercedes");
        cars.add("BMW");
        cars.add("Ferrari");
        cars.add("Ferrari");
        cars.add("Ford");
        cars.add("Opel");
        cars.add("Opel");
        cars.add("Honda");

        System.out.println("HashSet = " + cars);

        System.out.println("*********** LinkedHashSet ***********");
        //LinkedHashSet is an ordered collection of unique elements and maintains the insertion order.
        Set<String> cars2 = new LinkedHashSet<>();
        cars2.add("Mercedes");
        cars2.add("BMW");
        cars2.add("Ferrari");
        cars2.add("Ferrari");
        cars2.add("Ford");
        cars2.add("Opel");
        cars2.add("Opel");
        cars2.add("Honda");

        System.out.println("LinkedHashSet = " + cars2);

        System.out.println("*********** TreeSet ***********");
        //TreeSet is an ordered collection of unique elements and maintains the natural order of elements.
        TreeSet<Character> chars = new TreeSet<>();
        chars.add('P');
        chars.add('L');
        chars.add('R');
        chars.add('W');
        chars.add('W');
        chars.add('N');
        chars.add('B');
        chars.add('T');
        chars.add('T');
        chars.add('A');


        System.out.println("TreeSet (desc) = " + chars.descendingSet());
        System.out.println("chars = " + chars);
        System.out.println("HeadSet = " + chars.headSet('N', false)); //returns all elements less than or equal to the given element
        System.out.println("SubSet = " + chars.subSet('L', 'T')); //returns all elements between the given elements (inclusive), 'T' not included
        System.out.println("TailSet = " + chars.tailSet('N')); //returns all elements greater than or equal to the given element
        System.out.println("First = " + chars.first()); //returns the least element in the set
        System.out.println("Last = " + chars.last()); //returns the greatest element in the set
        System.out.println("Removed first = " + chars.pollFirst()); //removes and returns the least element in the set, or null if the set is empty
        System.out.println("Removed last = " + chars.pollLast()); //removes and returns the greatest element in the set, or null if the set is empty

        System.out.println("Final version of chars = " + chars);


        /*System.out.println("Lower = " + chars.lower('T')); //returns the greatest element less than the given element
        System.out.println("Floor = " + chars.floor('T')); //returns the greatest element less than or equal to the given element
        System.out.println("Higher = " + chars.higher('T')); //returns the least element greater than the given element
        System.out.println("Ceiling = " + chars.ceiling('T')); //returns the least element greater than or equal to the given element*/

    }
}


















