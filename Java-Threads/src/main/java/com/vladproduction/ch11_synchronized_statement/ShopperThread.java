package com.vladproduction.ch11_synchronized_statement;

/**
 * When using this inside the synchronized block, each instance of ShopperThread would lock on its own object,
 * meaning different threads could increment garlicCounter simultaneously.
 * This would lead to a data race and result in inconsistent or incorrect outcomes.
 * */
public class ShopperThread extends Thread{

    static int garlicCounter = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10_000_000; i++) {
            // If we use 'this' here, a data race may occur
            // because each instance of ShopperThread will synchronize on its own lock,
            // allowing both threads to increment garlicCounter concurrently.
            synchronized (ShopperThread.class){
                garlicCounter++;
            }
        }
    }

}
