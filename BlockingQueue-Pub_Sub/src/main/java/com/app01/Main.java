package com.app01;

public class Main {
    public static void main(String[] args) {

        SubscriberA subscriberA = new SubscriberA();
        Person person1 = new Person();
        person1.setName("John");

        person1.addSubscriber(subscriberA);

        person1.setName("John Doe");
        person1.setAge(23);

        Student student = new Student();
        SubscriberB subscriberB = new SubscriberB();
        student.addSubscriber(subscriberB);
        student.addSubscriber(subscriberA);
        student.setName("Jack");

        student.setAge(25);
        student.setAge(25);

    }
}
