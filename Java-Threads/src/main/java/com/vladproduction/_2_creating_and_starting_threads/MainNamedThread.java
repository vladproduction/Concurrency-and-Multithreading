package com.vladproduction._2_creating_and_starting_threads;

public class MainNamedThread {
    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            NamedThread thread = new NamedThread(i);
            thread.start();
        }
    }
}
