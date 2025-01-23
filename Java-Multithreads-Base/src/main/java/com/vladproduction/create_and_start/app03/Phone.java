package com.vladproduction.create_and_start.app03;

public class Phone {

    public synchronized  void makeCall(long duration){
        System.out.println("makeCall() - start "+ Thread.currentThread());
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
        System.out.println("makeCall() - end "+ Thread.currentThread());
    }

}
