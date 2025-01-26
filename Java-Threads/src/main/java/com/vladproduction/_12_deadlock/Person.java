package com.vladproduction._12_deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Person extends Thread{

    private Lock stick1;
    private Lock stick2;
    private static int sushi = 500_000;

    public Person(String name, Lock stick1, Lock stick2) {
        super(name);
        this.stick1 = stick1;
        this.stick2 = stick2;
    }

    @Override
    public void run() {
        while (sushi > 0){ // eat sushi until its all gone

            //pick up lock
            stick1.lock();
            stick2.lock();

            //take a piece of sushi
            if(sushi > 0){
                sushi--;
                //print info of remained pieces
                System.out.println(this.getName() + " took a piece sushi. Sushi remaining: " + sushi);
            }

            //leave the lock
            stick1.unlock();
            stick2.unlock();
        }
    }

    public static void main(String[] args) {
        Lock stick1 = new ReentrantLock();
        Lock stick2 = new ReentrantLock();
        Lock stick3 = new ReentrantLock();

        new Person("John", stick1, stick2).start();
        new Person("Bob", stick2, stick3).start();
        new Person("Peter", stick1, stick3).start();
    }
}
