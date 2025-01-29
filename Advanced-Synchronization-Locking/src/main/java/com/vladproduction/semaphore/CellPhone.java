package com.vladproduction.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class CellPhone extends Thread {

    private static Semaphore charger = new Semaphore(4); //to replace for 1 it will be as binary semaphore

    public CellPhone(String name) {
        super(name);
    }

    @Override
    public void run() {
        try{
            charger.acquire();
            System.out.println(this.getName() + " is charging...");
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            System.out.println(this.getName() + " is Done charging.");
            charger.release();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new CellPhone("CellPhone " + i).start();
        }
    }

}
