package com.vladproduction.java_collections.deque_interface;

import java.util.Deque;
import java.util.LinkedList;

public class DequeInterfaceExample {
    public static void main(String[] args) {

        //we can remove elements from both ends of the deque (FIFO)
        Deque<String> carsDeque = new LinkedList<>();
        carsDeque.add("Mercedes");
        carsDeque.add("BMW");
        carsDeque.add("Ferrari");
        carsDeque.addFirst("Ford");
        carsDeque.addLast("Opel");
        carsDeque.push("Honda");
        carsDeque.offer("Hyundai");
        carsDeque.offerFirst("Kia");
        carsDeque.offerLast("Renault");

        System.out.println("carsDeque = " + carsDeque);
        System.out.println("carsDeque.size() = " + carsDeque.size());

        System.out.println("Removed first element = " + carsDeque.removeFirst());
        System.out.println("carsDeque = " + carsDeque);

        System.out.println("Removed last element = " + carsDeque.removeLast());
        System.out.println("carsDeque = " + carsDeque);

    }
}
