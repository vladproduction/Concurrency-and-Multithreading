package com.vladproduction.java_collections.map_interface;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapImplementationsClassesExample {
    public static void main(String[] args) {

        System.out.println("*******HashMap*******");
        //HashMap
        //HashMap is a non-synchronized implementation of the Map interface.
        //HashMap is a dynamic, open-addressing hash table.
        //The HashMap class implements the Map interface, and makes no guarantees about the order of the map entries.
        //The HashMap class provides all of the optional map operations, and permits null values and the null key.
        //The HashMap class makes no guarantees as to the order of the map entries; in particular, it does not guarantee that the order will remain constant over time.
        //The HashMap class provides constant-time performance for the basic operations (get and put),
        //assuming the hash function disperses the elements properly among the buckets.

        Map<String, Integer> agesMap = new HashMap<>();
        agesMap.put("Alex", 40);
        agesMap.put("James", 20);
        agesMap.put("James", 30);
        agesMap.put("Ronaldo", 35);
        agesMap.put("Merry", 25);

        System.out.println("agesMap = " + agesMap);

        System.out.println();

        System.out.println("*******LinkedHashMap*******");
        //LinkedHashMap
        //LinkedHashMap is a non-synchronized implementation of the Map interface.
        //LinkedHashMap is a dynamic, open-addressing hash table.
        //The LinkedHashMap class implements the Map interface, and makes no guarantees about the order of the map entries.
        //The LinkedHashMap class provides all of the optional map operations, and permits null values and the null key.
        //The LinkedHashMap class makes no guarantees as to the order of the map entries; in particular, it does not guarantee that the order will remain constant over time.
        //The LinkedHashMap class provides constant-time performance for the basic operations (get and put),
        //assuming the hash function disperses the elements properly among the buckets.
        //The iteration order of a linked hash map is the order in which the entries were inserted into the map.
        //This iteration order is guaranteed to be the same in any given implementation of the Java platform.

        Map<String, String> colorsMap = new LinkedHashMap<>();
        colorsMap.put("BMW", "Blue");
        colorsMap.put("Opel", "Green");
        colorsMap.put("Ferrari", "White");
        colorsMap.put("Ferrari", "Red");
        colorsMap.put("Mercedes", "Black");

        System.out.println("colorsMap = " + colorsMap);

        System.out.println();

        System.out.println("*******TreeMap*******");
        //TreeMap
        //TreeMap is a non-synchronized implementation of the Map interface.
        //TreeMap is a self-balancing binary search tree.
        //The TreeMap class implements the Map interface, and makes no guarantees about the order of the map entries.
        //The TreeMap class provides all of the optional map operations, and permits null values and the null key.
        //The TreeMap class makes no guarantees as to the order of the map entries; in particular, it does not guarantee that the order will remain constant over time.
        //The TreeMap class provides constant-time performance for the basic operations (get and put),
        //assuming the hash function disperses the elements properly among the tree nodes.
        //All of the optional map operations, such as putAll, are performed in constant time.

        TreeMap<Integer, String> phoneCodes = new TreeMap<>();
        phoneCodes.put(90, "Turkey");
        phoneCodes.put(44, "United Kingdom");
        phoneCodes.put(33, "France");
        phoneCodes.put(49, "Germany");
        phoneCodes.put(20, "Egypt");
        phoneCodes.put(49, "Germany");
        phoneCodes.put(91, "India");

        System.out.println("phoneCodes = " + phoneCodes);
        //System.out.println("Descending map: " + phoneCodes.descendingMap());
        //System.out.println("Descending key set: " + phoneCodes.descendingKeySet());

        phoneCodes.remove(44);
        for (Map.Entry<Integer, String> integerStringEntry : phoneCodes.entrySet()) {
            Integer key = integerStringEntry.getKey();
            String value = integerStringEntry.getValue();
            System.out.println("Phone code " + key + " is: " + value);
        }

    }
}
