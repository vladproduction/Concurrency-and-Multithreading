package com.vladproduction.ch02_creating_and_starting_threads;

public class MainNamedThread {

    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            NamedThread thread = new NamedThread(i);
            thread.start();
        }
    }

    static class NamedThread extends Thread{

        private final int number;

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

}
