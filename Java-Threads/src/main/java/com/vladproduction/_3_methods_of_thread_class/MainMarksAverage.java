package com.vladproduction._3_methods_of_thread_class;

public class MainMarksAverage {
    public static void main(String[] args) {
        int[] marks = new int[10];
        Thread marksThread = new Thread(new Marks(marks));
        Thread averageThread = new Thread(new Average(marks));
        marksThread.start();
        try {
            marksThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        averageThread.start();
    }
}
