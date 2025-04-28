package com.vladproduction.ch14_wait_notify.simple_example_wait_notify;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Office {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Office day: started");

        //create a shared resource (which is Phone)
        Phone phone = new Phone();

        //create Employees and Boss
        EmployeeA employeeA = new EmployeeA(phone);
        EmployeeB employeeB = new EmployeeB(phone);
        EmployeeC employeeC = new EmployeeC(phone);
        Boss boss = new Boss(phone);

        starterEmployeesThreads(employeeA, employeeB, employeeC);

        //give employees time to start waiting
        Thread.sleep(1000);

        //Boss comes to the office and takes (block) the phone
        boss.start();

        //wait until all operations are finished
        joinerMethod(employeeA, employeeB, employeeC, boss);

        System.out.println("Office day: finished");

    }

    private static void joinerMethod(Thread... threads) {
        Arrays.stream(threads).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

    }

    private static void starterEmployeesThreads(Thread... threads) {
        Arrays.stream(threads).forEach(Thread::start);
    }

}
