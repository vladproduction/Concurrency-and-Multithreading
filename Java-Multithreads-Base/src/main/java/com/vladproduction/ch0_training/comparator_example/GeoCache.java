package com.vladproduction.ch0_training.comparator_example;

import java.util.Arrays;
import java.util.Comparator;

public class GeoCache {
    public static void main(String[] args) {

        String[] s = {"map", "pen", "marble", "key"};
        Othello o = new Othello();
        Arrays.sort(s, o);

        for (String s2 : s) System.out.print(s2 + " ");
        System.out.println(Arrays.binarySearch(s, "map"));

//        output: pen marble map key -1
    }

    static class Othello implements Comparator<String> {
        public int compare(String a, String b) {
            return b.compareTo(a);
        }
    }
}

/**
 * The Arrays.binarySearch method searches for the string "map" in the sorted array s using the binary search algorithm.
 * However, since binarySearch assumes the array is sorted in ascending order but our array is sorted in descending order,
 * it will not find "map" in the expected way.
 *
 * Consequently, the binary search will return a value that indicates the position where "map" could be inserted to maintain the sort order.
 * Since "map" is less than "pen" and in a descending sorted array, it will return -1 (indicating that the element is not found).
 * */