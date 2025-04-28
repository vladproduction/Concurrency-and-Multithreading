package com.vladproduction.ch14_wait_notify.simple_example_wait_notify;

public class Boss extends Thread {

    private final Phone phone;

    public Boss(Phone phone) {
        this.phone = phone;
        setName("Boss");
    }

    @Override
    public void run(){
        System.out.println("Boss: entered the office");

        // Boss takes the phone for an important call
        synchronized (phone){
            System.out.println("Boss: took the phone for an important call");
            phone.setPhoneAvailable(false);

            //Boss makes a call
            phone.makeCall(5000);

            // Call finished, phone becomes available
            phone.setPhoneAvailable(true);
            System.out.println("Boss: finished the call, notifying waiting employees");

            // Notify all employees that the phone is available
            phone.notifyAll();
        }

    }
}
