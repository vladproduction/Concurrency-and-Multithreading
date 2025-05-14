package com.vladproduction.java_collections.list_interafce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingArrayList {
    public static void main(String[] args) {

        System.out.println("------------Sorting Double List----------");
        List<Double> list1 = new ArrayList<>();
        list1.add(2.3);
        list1.add(11.0);
        list1.add(4.6);
        list1.add(-3.6);
        list1.add(0.8);
        list1.add(12.0);

        System.out.println("Unsorted list: " + list1);

        Collections.sort(list1);

        System.out.println("Sorted list: " + list1);


        System.out.println("------------Sorting Integer List----------");
        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(3);
        list2.add(-5);
        list2.add(10);
        list2.add(2);
        list2.add(9);

        System.out.println("Unsorted list: " + list2);

        Collections.sort(list2);

        System.out.println("Sorted list: " + list2);

        System.out.println("------------Sorting Character List----------");
        List<Character> list3 = new ArrayList<>();
        list3.add('F');
        list3.add('B');
        list3.add('b');
        list3.add('H');
        list3.add('c');
        list3.add('A');

        System.out.println("Unsorted list: " + list3);

        Collections.sort(list3);

        System.out.println("Sorted list: " + list3);

        System.out.println("------------Sorting String List----------");
        List<String> list4 = new ArrayList<>();
        list4.add("cat");
        list4.add("Dog");
        list4.add("Monkey");
        list4.add("Lion");
        list4.add("elephant");
        list4.add("Cow");

        System.out.println("Unsorted list: " + list4);

        Collections.sort(list4);

        System.out.println("Sorted list: " + list4);

        System.out.println("------------Sorting String List 2----------");
        List<String> list5 = new ArrayList<>();
        list5.add("3");     //3
        list5.add("300");   //3
        list5.add("20");    //2
        list5.add("35");    //3
        list5.add("37");    //3
        list5.add("31");    //3
        list5.add("4");     //4
        list5.add("70");    //7
        list5.add("ant");
        list5.add("Zebra");

        System.out.println("Unsorted list: " + list5);

        Collections.sort(list5);

        System.out.println("Sorted list: " + list5);  //20 3 300 31 35 37 4 70 Zebra ant


    }
}
