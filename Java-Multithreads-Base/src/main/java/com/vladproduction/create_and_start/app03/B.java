package com.vladproduction.create_and_start.app03;

public class B implements Runnable {
    private final Phone phone;

    public B(Phone phone) {
        this.phone = phone;
    }

    @Override
    public void run() {
        System.out.println("B started");
        phone.makeCall(7000);
        System.out.println("B finished");
    }
}
