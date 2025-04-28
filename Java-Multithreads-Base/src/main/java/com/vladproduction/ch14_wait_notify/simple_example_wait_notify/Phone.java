package com.vladproduction.ch14_wait_notify.simple_example_wait_notify;


public class Phone {

    private boolean phoneAvailable = true;

    public synchronized void makeCall(long duration){
        System.out.println(Thread.currentThread().getName() + ": Making a call...");

        try{
            Thread.sleep(duration);
        } catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }

        System.out.println(Thread.currentThread().getName() + ": Call finished!");
    }

    public boolean isPhoneAvailable(){
        return phoneAvailable;
    }

    public void setPhoneAvailable(boolean phoneAvailable) {
        this.phoneAvailable = phoneAvailable;
    }
}
