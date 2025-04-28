package com.vladproduction.ch14_wait_notify.simple_example_wait_notify;

public class EmployeeB extends Thread{
    private final Phone phone;

    public EmployeeB(Phone phone) {
        this.phone = phone;
        setName("EmployeeB");
    }

    @Override
    public void run() {
        System.out.println("EmployeeB: needs to make a call");

        synchronized (phone) {
            System.out.println("EmployeeB: checking if phone is available");

            // While the phone is not available, wait
            while (!phone.isPhoneAvailable()) {
                try {
                    System.out.println("EmployeeB: phone is not available, waiting");
                    phone.wait();
                    System.out.println("EmployeeB: notified that phone might be available");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Phone is available, make the call
            System.out.println("EmployeeB: got the phone");
            phone.makeCall(2000);
        }

        System.out.println("EmployeeB: finished tasks");
    }
}
