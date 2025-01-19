package com.vladproduction._10_synchronized_method;

public class SynchronizedMethodDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread person1 = new ShopperPerson();
        Thread person2 = new ShopperPerson();

        person1.start();
        person2.start();

        person1.join();
        person2.join();

        System.out.println("We should buy " + ShopperPerson.garlicCount + " garlic.");

    }
}
