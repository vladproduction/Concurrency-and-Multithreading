package com.vladproduction.java_collections.list_interafce;

import java.util.ArrayList;

public class ArrayListExamples {
    public static void main(String[] args) {

        ArrayList<Integer> ages = new ArrayList<>();
        ages.add(20);
        ages.add(25);
        ages.add(30);

        System.out.println("ages: " + ages); //order is preserved
        System.out.println("ages.get(1): " + ages.get(1));

        //ArrayList of Objects
        ArrayList<Object> mixList = new ArrayList<>();
        mixList.add("Java");
        mixList.add(10);
        mixList.add(true);

        System.out.println("mixList: " + mixList);

        //elements can be added at any index, others existing elements are shifted to the right
        ArrayList<String> animals = new ArrayList<>();
        animals.add("Lion");
        animals.add("Cat");
        animals.add(2, "Dog");
        animals.add(1, "Cow");

        System.out.println("animals: " + animals);

        //delete an element (by Object or index)
        animals.remove(2);
        System.out.println("animals: " + animals);

        animals.remove("Lion");
        System.out.println("animals: " + animals);

        //alter by set(): replaces the element at the given index
        animals.set(1, "Elephant");
        System.out.println("animals: " + animals);

        //ArrayList of String
        ArrayList<String> cars = new ArrayList<>();
        System.out.println("cars.size() = " + cars.size()); //0
        System.out.println("cars.isEmpty() ? = " + cars.isEmpty()); //true
        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.add("Mazda");
        System.out.println("cars.size() = " + cars.size()); //4
        System.out.println("cars.isEmpty() ? = " + cars.isEmpty()); //false

        //contains() method:
        System.out.println("cars.contains(\"Opel\") ? = " + cars.contains("Opel")); //false
        System.out.println("cars.contains(\"BMW\") ? = " + cars.contains("BMW")); //true

        //clear method for deleting all elements
        cars.clear();
        System.out.println("cars.size() = " + cars.size()); //0
        System.out.println("cars.isEmpty() ? = " + cars.isEmpty()); //true

        //equals() method for ArrayList comparison
        ArrayList<String> names = new ArrayList<>();
        names.add("David");
        names.add("John");
        names.add("Bob");

        ArrayList<Object> names2 = new ArrayList<>();
        names2.add("John");
        names2.add("Bob");
        names2.add("David");

        System.out.println("names.equals(names2) ? = " + names.equals(names2)); //compare in order (return true if they are equal)

        //no way to init ArrayList as array:
        //ArrayList<Integer> integers = {1,2,3,4,5};



    }
}
