package com.vladproduction.condition_variable;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Example of using Condition with multiple threads and different objects.
 * In this scenario, we will simulate a restaurant where multiple customers are waiting for available tables to sit and eat.
 * The program will demonstrate customers trying to acquire tables, checking for availability,
 * and how the condition variable is used to manage access to limited tables.
 * */
public class Restaurant {

    private static final int TOTAL_TABLES = 5;
    private static int availableTables = TOTAL_TABLES;
    private static final Lock lock = new ReentrantLock();
    private static final Condition tableAvailable = lock.newCondition();

    public static class Customer extends Thread {

        private int customerId;

        public Customer(int customerId) {
            this.customerId = customerId;
        }

        @Override
        public void run() {
            try{
                lock.lock();

                while (availableTables <= 0) {
                    System.out.printf("Customer %d is waiting for table available.\n", customerId);
                    tableAvailable.await();
                }
                availableTables--;
                System.out.printf("Customer %d has taken a table. Available tables left: %d\n", customerId, availableTables);

                //simulate eating time
                Thread.sleep(2000);

                //customer done eating; increment available tables
                availableTables++;
                System.out.printf("Customer %d is done eating. Available tables left: %d\n", customerId, availableTables);

                //signal to others for available tables:
                tableAvailable.signal();



            }catch (InterruptedException e){
                e.printStackTrace();
            }
            finally {
                lock.unlock();
            }
        }
    }



    public static void main(String[] args) {

        //simulating 10 customers waiting for tables:
        for (int i = 0; i < 10; i++) {
            new Thread(new Customer(i)).start();
        }

    }

}
