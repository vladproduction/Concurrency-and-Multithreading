package com.vladproduction.queue_samples.priorityQueue;

import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {

        Person p1 = new Person(5, 26, "John", "manager");
        Person p2 = new Person(7, 29, "Dark", "account");
        Person p3 = new Person(2, 36, "Kelly", "driver");
        Person p4 = new Person(50, 22, "Barn", "secretary");
        Person p5 = new Person(1, 27, "Gregg", "seo");

        Queue<Person> personQueue = new PriorityQueue<>();
        personQueue.add(p1);
        personQueue.add(p2);
        personQueue.add(p4);
        personQueue.add(p3);
        personQueue.add(p5);

        personQueue.forEach(System.out::println);

        personQueue.remove();
        System.out.println("\tafter remove:");
        personQueue.forEach(System.out::println);

        personQueue.remove();
        System.out.println("\tafter remove:");
        personQueue.forEach(System.out::println);

        personQueue.remove();
        System.out.println("\tafter remove:");
        personQueue.forEach(System.out::println);

        personQueue.remove();
        System.out.println("\tafter remove:");
        personQueue.forEach(System.out::println);
    }
}
