package com.vladproduction.producer_consumer;

import java.util.concurrent.BlockingQueue;

public class SoupConsumer extends Thread {

    private BlockingQueue servingLine;

    public SoupConsumer(BlockingQueue servingLine) {
        this.servingLine = servingLine;
    }

    @Override
    public void run() {
        while (true){
            try{
                String bowl = (String)servingLine.take();
                if(bowl == "No more soup!"){
                    break;
                }
                System.out.printf("Consuming bowl: %s\n", bowl);
                Thread.sleep(300);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
