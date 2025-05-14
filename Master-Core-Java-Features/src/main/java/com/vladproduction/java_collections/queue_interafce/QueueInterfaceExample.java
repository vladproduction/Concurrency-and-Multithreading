package com.vladproduction.java_collections.queue_interafce;

import java.util.LinkedList;
import java.util.Queue;

public class QueueInterfaceExample {
    public static void main(String[] args) {

        Queue<String> carsQueue = new LinkedList<>();
        carsQueue.add("Mercedes"); //throws an exception if the queue is full
        carsQueue.offer("Ferrari"); //returns false if the queue is full, does not throw an exception
        carsQueue.offer("Ford");
        carsQueue.offer("BMW");
        carsQueue.offer("Opel");

        System.out.println("carsQueue = " + carsQueue);
        System.out.println("carsQueue.size() = " + carsQueue.size());

        System.out.println("First element = " + carsQueue.element()); //throws an exception if the queue is empty
        System.out.println("First element = " + carsQueue.peek()); //returns null if the queue is empty, does not throw an exception


        //System.out.println("carsQueue.poll() = " + carsQueue.poll()); //returns null if the queue is empty, does not throw an exception
        System.out.println("carsQueue = " + carsQueue);
        System.out.println("carsQueue.size() = " + carsQueue.size());

        //System.out.println("carsQueue.remove(): FIFO = " + carsQueue.remove()); //throws an exception if the queue is empty
        System.out.println("carsQueue = " + carsQueue);
        System.out.println("carsQueue.size() = " + carsQueue.size());

        //FIFO: first in first out
        int size = carsQueue.size();
        for (int i = 0; i < size; i++) {
            System.out.println("Removed element = " + carsQueue.poll());
        }

        System.out.println("Last version of carsQueue = " + carsQueue);

        System.out.println("First element = " + carsQueue.peek()); //null
        //System.out.println("First element = " + carsQueue.element()); //exception
        System.out.println("carsQueue.poll() = " + carsQueue.poll());//null
        //System.out.println("carsQueue.poll() = " + carsQueue.remove());//exception

    }
}
