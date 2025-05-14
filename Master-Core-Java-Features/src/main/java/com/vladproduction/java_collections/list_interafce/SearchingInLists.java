package com.vladproduction.java_collections.list_interafce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchingInLists {
    public static void main(String[] args) {

        List<Integer> numbersList = new ArrayList<>();
        numbersList.add(19);
        numbersList.add(11);
        numbersList.add(17);
        numbersList.add(15);
        numbersList.add(39);
        numbersList.add(27);
        numbersList.add(22);

        Collections.sort(numbersList);

        System.out.println(Collections.binarySearch(numbersList, 17)); //2
        System.out.println(Collections.binarySearch(numbersList, 27)); //5
        System.out.println(Collections.binarySearch(numbersList, 8));  //-1

    }
}
