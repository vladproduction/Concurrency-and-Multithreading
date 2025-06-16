package com.vladproduction.ch04_execution_scheduling;

class VegetableChapping extends Thread{

    public int vegetableCount = 0;
    public static boolean choppingStatus = true;

    public VegetableChapping(String name) {
        this.setName(name); // setter came from Thread class;
    }

    @Override
    public void run() {
        while (choppingStatus){
            System.out.println(this.getName() + "chopped a vegetable!");
            vegetableCount++;
        }
    }
}
