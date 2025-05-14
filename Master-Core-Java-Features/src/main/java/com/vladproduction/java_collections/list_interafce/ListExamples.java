package com.vladproduction.java_collections.list_interafce;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class ListExamples {
    public static void main(String[] args) {

        List<String> list1 = new ArrayList<>(); //Polymorphism
        List<Integer> list2; //Declaration
        list2 = new ArrayList<>(); //Initialization

        //List<double> list3 = new ArrayList<>();// no primitives allowed

        Collection<Integer> list4 = new ArrayList<>();

        //row type (not safe)
        List list5 = new ArrayList();//row type
        list5.add("Hello World");
        String message = (String)list5.get(0);

        //generic type (safe)
        List<String> list6 = new ArrayList<>();
        list6.add("Hello World");
        String message2 = list6.get(0); //no need to cast




    }
}
