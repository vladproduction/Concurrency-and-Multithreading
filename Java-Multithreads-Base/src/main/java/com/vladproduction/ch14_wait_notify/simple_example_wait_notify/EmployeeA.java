package com.vladproduction.ch14_wait_notify.simple_example_wait_notify;

public class EmployeeA extends Thread {

    private final Phone phone;

    public EmployeeA(Phone phone) {
        this.phone = phone;
        setName("EmployeeA");
    }

    @Override
    public void run(){
        System.out.println("EmployeeA: needs to make a call");
        synchronized (phone){
            System.out.println("EmployeeA: checking if phone is available");

            //while the phone is busy, wait
            while(!phone.isPhoneAvailable()){
                try {
                    System.out.println("EmployeeA: phone is not available, waiting");
                    phone.wait();
                    System.out.println("EmployeeA: notified that phone might be available");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Phone is available, make the call
            System.out.println("EmployeeA: got the phone");
            phone.makeCall(3000);

        }

        System.out.println("EmployeeA: finished tasks");
    }

}
