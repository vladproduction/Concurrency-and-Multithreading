package com.vladproduction.thread_safety_and_immutability;

public class ImmutabilityTest {
    public static void main(String[] args) {

        Person person = new Person("Alice", 20);

        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + ": " + person.getName() + " is " + person.getAge() + " years old.");
        };

        Thread t1 = new Thread(task, "Thread 1");
        Thread t2 = new Thread(task, "Thread 2");
        Thread t3 = new Thread(task, "Thread 3");

        t1.start();
        t2.start();
        t3.start();

    }
}
