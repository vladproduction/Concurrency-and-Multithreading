package com.vladproduction.producer_consumer_system;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {

    private final SharedBuffer sharedBuffer;
    private int orderId = 0;

    public Producer(SharedBuffer sharedBuffer) {
        this.sharedBuffer = sharedBuffer;
    }

    @Override
    public void run() {
        String[] products = {"Laptop", "Phone", "Tablet", "Monitor", "Headphones", "Keyboard"};
        Random random = new Random();

        while (true) {
            try{
                String productName = products[random.nextInt(products.length)];
                int quantity = random.nextInt(1, 6);

                Order order = new Order(++orderId, productName, quantity);
                sharedBuffer.produce(order);
                TimeUnit.MILLISECONDS.sleep(1000L);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
