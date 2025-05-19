package com.vladproduction.ch0_training.race_condition_solution;

public class MyData {

    private int value;


    public synchronized void increment(){
        value++;
    }

    public synchronized void decrement(){
        value--;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
