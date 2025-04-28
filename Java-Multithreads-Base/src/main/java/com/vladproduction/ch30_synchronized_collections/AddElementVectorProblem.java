package com.vladproduction.ch30_synchronized_collections;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class AddElementVectorProblem {
    public static void main(String[] args) throws InterruptedException {

        /**
         * add() and contain() is synchronized in Vector, but it used inside sub-method addElementIfNotExist,
         * which is not synchronized;
         * to avoid this problem, we have to synchronize the whole method or using synchronized block
         * */

        List<Integer> values = new Vector<>();
        values.add(1);
        values.add(2);
        values.add(3);

        Runnable addingTask = () -> addElementIfNotExist(values, 4);

        Thread thread1 = new Thread(addingTask);
        Thread thread2 = new Thread(addingTask);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(values); //[1, 2, 3, 4, 4] but wanted to expect [1, 2, 3, 4]

    }

    /**
     * values as storage;
     * element trying to add;
     * */
    private static void addElementIfNotExist(List<Integer> values, Integer element)  {
       try{
           if (!values.contains(element)) {
               TimeUnit.MILLISECONDS.sleep(100);
               values.add(element);
           }
       }catch (InterruptedException e){
           Thread.currentThread().interrupt();
       }
    }

}
