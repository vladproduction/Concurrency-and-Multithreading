package com.vladproduction.ch0_training.race_condition_solution;

public class DecrementThread extends Thread {

    private final MyData myData;

    public DecrementThread(MyData myData) {
        this.myData = myData;
    }

    @Override
    public void run() {
        System.out.println("DecrementThread started");

        for (int i = 1; i <= 100_000; i++) {
            myData.decrement();
        }

        System.out.println("DecrementThread finished");
    }
}
