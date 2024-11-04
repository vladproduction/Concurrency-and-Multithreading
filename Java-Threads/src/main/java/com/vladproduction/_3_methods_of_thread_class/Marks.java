package com.vladproduction._3_methods_of_thread_class;

public class Marks implements Runnable {
    int[] marks;

    public Marks(int[] marks) {
        this.marks = marks;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Double point = Math.random() * 100;
            marks[i] = point.intValue();
            System.out.println("Mark entered: " + marks[i]);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
