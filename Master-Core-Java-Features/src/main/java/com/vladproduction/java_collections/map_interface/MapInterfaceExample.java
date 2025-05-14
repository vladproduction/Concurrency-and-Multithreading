package com.vladproduction.java_collections.map_interface;

import java.util.HashMap;
import java.util.Map;

public class MapInterfaceExample {
    public static void main(String[] args) {

        //key-value pairs
        //don't allow duplicate keys but allow duplicate values
        //to no preserve the order of the elements
        Map<String, Integer> carsMap = new HashMap<>();
        carsMap.put("Mercedes", 40000);
        carsMap.put("Ferrari", 35000);
        carsMap.put("BMW", 25000);
        carsMap.put("BMW", 30000); //the last added value will be used
        carsMap.put("Ford", 20000);

        System.out.println("carsMap = " + carsMap);
        //System.out.println("Price of BMW = " + carsMap.get("BMW"));

        carsMap.remove("Ford");
        carsMap.replace("Mercedes", 45000);
        System.out.println("Last version carsMap = " + carsMap);

        System.out.println("-------for loop--------");
        //access data by the for loop
        for(String car : carsMap.keySet()){
            System.out.println("Price of car " + car + " is: $" + carsMap.get(car));
        }

        System.out.println("-------for each loop--------");
        //for each loop
        for (Map.Entry<String, Integer> mapEntry : carsMap.entrySet()) {
            System.out.println("Price of car " + mapEntry.getKey() + " is: $" + mapEntry.getValue());
        }
        System.out.println("-------forEach loop (lambda expression)--------");
        carsMap.forEach((car, price) -> System.out.println("Price of car " + car + " is: $" + price));

    }
}
