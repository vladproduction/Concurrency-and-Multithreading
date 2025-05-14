package com.vladproduction.java_collections.list_interafce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConversionLists_and_Array {
    public static void main(String[] args) {

        //conversion List -> Array

        List<String> animalList = new ArrayList<>();
        animalList.add("Cat");
        animalList.add("Dog");
        animalList.add("Cow");
        animalList.add("Lion");

        /*
        // 1) for loop used:
        String[] animalArray = new String[animalList.size()];
        for (int i = 0; i < animalList.size(); i++) {
            animalArray[i] = animalList.get(i);
        }*/

        // 2) toArray() method used:
        //Object[] animalArray = animalList.toArray();

        // 3) as a parameter:
        //String[] animalArray = new String[animalList.size()]; //size might not be larger than a list, otherwise it will print null`s
        //animalArray = animalList.toArray(animalArray);
        //shorter in one line:
        //String[] animalArray = animalList.toArray(new String[animalList.size()]);
        //or
        String[] animalArray = animalList.toArray(new String[0]);

        //if we add more elements into the list:
        animalList.add("Elephant"); //


        System.out.println("animalList = " + animalList);
        System.out.println("animalArray: " + Arrays.asList(animalArray));
        System.out.println("------------------------");

        //conversion Array -> List
        String[] carArray = {"Volvo", "BMW", "Ford", "Mazda"};

        // 1) asList() method:
        List<String> carList = Arrays.asList(carArray);

        // 2) asList() in constructor parameter:
        //ArrayList<String> carList = new ArrayList<>(Arrays.asList(carArray));

        // 3) addAll() method used:
        //ArrayList<String> carList = new ArrayList<>();
        //Collections.addAll(carList, carArray);
        
        // 4) for loop used:
        /*for (String car : carArray) {
            carList.add(car);
        }*/

        //carList.add("Opel"); //we can add more elements into the list after conversion by for each loop

        //UnsupportedOperationException: after add more elements into the list after conversion by asList()
        //asList() method is unmodifiable (not allowed to add or delete elements)

        carArray[1] = "Hyundai"; //we can change elements in the array after conversion by asList()

        carList.set(2, "Honda"); //we can change elements in the list after conversion by asList()


        System.out.println("carArray = " + Arrays.toString(carArray));
        System.out.println("carList  = " + carList);

    }
}
