package com.vladproduction.ch0_training.race_condition_solution;

public class Main02 {
    public static void main(String[] args) {

        MyData myData = new MyData();

        Thread incrementThread = new IncrementThread(myData);
        Thread decrementThread = new DecrementThread(myData);

        incrementThread.start();
        decrementThread.start();

        try{
            incrementThread.join();
            decrementThread.join();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        System.out.println(myData);

    }
}
