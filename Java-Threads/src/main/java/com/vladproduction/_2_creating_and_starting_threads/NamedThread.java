package com.vladproduction._2_creating_and_starting_threads;

public class NamedThread extends Thread{

    private int number;

    public NamedThread(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 0; i <= 5; i++) {
            System.out.println("thread:" + number + " is printed " + i);
        }
    }
}
