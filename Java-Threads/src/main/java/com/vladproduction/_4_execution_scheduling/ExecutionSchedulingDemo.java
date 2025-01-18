package com.vladproduction._4_execution_scheduling;

/**
 * Two threads executes their onw job
 * */
public class ExecutionSchedulingDemo {
    public static void main(String[] args) throws InterruptedException {
        VegetableChapping person1 = new VegetableChapping("Person #1");
        VegetableChapping person2 = new VegetableChapping("Person #2");

        person1.start();
        person2.start();
        Thread.sleep(100);
        VegetableChapping.choppingStatus = false;
        person1.join();
        person2.join();

        System.out.printf("%s chopped %d vegetables.\n", person1.getName(), person1.vegetableCount);
        System.out.printf("%s chopped %d vegetables.\n", person2.getName(), person2.vegetableCount);
        System.out.printf("%s winner!", person1.vegetableCount > person2.vegetableCount ?  person1.getName() : person2.getName());
    }
}
