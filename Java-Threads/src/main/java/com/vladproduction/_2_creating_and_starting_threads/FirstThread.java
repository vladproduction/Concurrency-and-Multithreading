package com.vladproduction._2_creating_and_starting_threads;

public class FirstThread extends Thread{

    @Override
    public void run() {
        for (int i = 0; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
