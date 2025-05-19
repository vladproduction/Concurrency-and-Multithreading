package com.vladproduction.ch0_training.race_condition_solution;

public class IncrementThread extends Thread {

    private final MyData myData;

    public IncrementThread(MyData myData) {
        this.myData = myData;
    }

    @Override
    public void run() {
        System.out.println("IncrementThread started");

        for (int i = 1; i <= 100_000; i++) {
            myData.increment();
        }

        System.out.println("IncrementThread finished");
    }
}
