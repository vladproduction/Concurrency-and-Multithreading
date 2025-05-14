package com.vladproduction.producer_consumer_system;

import java.util.LinkedList;
import java.util.List;

public class SharedBuffer {

    private final List<Order> buffer = new LinkedList<>();
    private final int capacity;

    public SharedBuffer(int capacity) {
        this.capacity = capacity;
    }

    //produce method
    public synchronized void produce(Order order) throws InterruptedException {
        while (buffer.size() == capacity) {
            System.out.println("List is full");
            wait();
        }
        buffer.add(order);
        System.out.println("New order added: " + order);
        notifyAll();
    }


    //consume method
    public synchronized Order consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.println("List is empty");
            wait();
        }
        Order order = buffer.removeFirst();
        System.out.println("Processing order: " + order);
        notifyAll();
        return order;
    }


}
