package com.vladproduction.java_collections.iterator_listerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class IteratorListIterator {
    public static void main(String[] args) {

        List<Integer> numbersList = new ArrayList<>();
        int i = 1;
        while(i < 51){
            numbersList.add(i);
            i++;
        }
        System.out.println("numbersList: " + numbersList);

        //ListIterator
        ListIterator<Integer> listIterator = numbersList.listIterator();
        System.out.print("Indexes: ");
        while(listIterator.hasNext()){
            int indexes = listIterator.nextIndex();
            System.out.print(indexes + " ");

            int eachNumber = listIterator.next();
            if(eachNumber % 3 != 0){
                listIterator.set(-1);
            }
        }
        System.out.println();
        System.out.println("new numbers list: " + numbersList);


/*

        //Iterator
        Iterator<Integer> iterator = numbersList.iterator();
        while(iterator.hasNext()){
            int eachNumber = iterator.next();
            if(eachNumber % 3 != 0){
                iterator.remove();
            }
        }
        System.out.println("new numbers list: " + numbersList);
*/

    }
}
