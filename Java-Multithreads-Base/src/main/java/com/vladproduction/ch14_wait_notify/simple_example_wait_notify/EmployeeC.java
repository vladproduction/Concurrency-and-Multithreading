package com.vladproduction.ch14_wait_notify.simple_example_wait_notify;

public class EmployeeC extends Thread {
    private final Phone phone;

    public EmployeeC(Phone phone) {
        this.phone = phone;
        setName("EmployeeC");
    }

    @Override
    public void run() {
        System.out.println("EmployeeC: needs to make a call");

        synchronized (phone) {
            System.out.println("EmployeeC: checking if phone is available");

            // While phone is not available, wait
            while (!phone.isPhoneAvailable()) {
                try {
                    System.out.println("EmployeeC: phone is not available, waiting");
                    phone.wait();
                    System.out.println("EmployeeC: notified that phone might be available");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Phone is available, make the call
            System.out.println("EmployeeC: got the phone");
            phone.makeCall(4000);
        }

        System.out.println("EmployeeC: finished tasks");
    }

}
