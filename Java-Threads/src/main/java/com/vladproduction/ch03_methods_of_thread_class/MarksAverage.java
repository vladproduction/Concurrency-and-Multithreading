package com.vladproduction.ch03_methods_of_thread_class;

public class MarksAverage {
    public static void main(String[] args) {

        int[] marks = new int[10];

        Thread marksThread = new Thread(new Marks(marks));
        Thread averageThread = new Thread(new Average(marks));

        marksThread.start();

        try {
            marksThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        averageThread.start();
    }

    static class Marks implements Runnable {

        int[] marks;

        public Marks(int[] marks) {
            this.marks = marks;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                double point = Math.random() * 100;
                marks[i] = (int) point;
                System.out.println("Mark entered: " + marks[i]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

    }

    static class Average implements Runnable{

        int[] marks;

        public Average(int[] marks) {
            this.marks = marks;
        }

        @Override
        public void run() {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                System.out.println("Adding mark: " + marks[i]);
                sum += marks[i];
            }
            System.out.println("Average: " + sum / 10);
        }
    }





}
