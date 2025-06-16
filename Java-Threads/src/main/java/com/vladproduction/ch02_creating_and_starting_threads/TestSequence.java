package com.vladproduction.ch02_creating_and_starting_threads;

public class TestSequence extends Thread {

    private final String name;

    public TestSequence(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + " " + i);
        }
    }

    public static void main(String[] args) {

        TestSequence test1 = new TestSequence("#1: ");
        TestSequence test2 = new TestSequence("#2: ");
        TestSequence test3 = new TestSequence("#3: ");

        test1.start();
        test2.start();
        test3.start();
    }

}
