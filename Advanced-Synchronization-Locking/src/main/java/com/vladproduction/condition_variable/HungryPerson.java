package com.vladproduction.condition_variable;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HungryPerson extends Thread {

    private int personId;
    private static final Lock lock = new ReentrantLock();
    private static  int servings = 11;
    private static final Condition condition = lock.newCondition();

    public HungryPerson(int personId) {
        this.personId = personId;
    }

    @Override
    public void run() {
        while (servings > 0) {
            lock.lock();
            try{

                //servings % 10 --> amount of potential soup takers (threads)
                while ((personId != servings % 10) && servings > 0) { //check if it`s your turn
                    System.out.printf("Hungry person id: %d checked ...\n", personId);
                    condition.await();
                }
                if(servings > 0){
                    servings--; //your turn to take soup
                    System.out.printf("Hungry person id: %d taken soup! Servings left: %d \n", personId, servings);
                    //could be hogging if we use just signal() for more than 2 threads; must to signalAll();
//                    condition.signal(); //signal to other available to take some soup
                    condition.signalAll(); //signal to others (more than 2) available to take
                }

            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new HungryPerson(i).start();
        }
    }
}














