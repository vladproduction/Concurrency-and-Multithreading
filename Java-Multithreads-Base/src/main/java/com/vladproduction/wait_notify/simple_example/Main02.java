package com.vladproduction.wait_notify.simple_example;

public class Main02 {

    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();

        ClassA classA = new ClassA(phone);
        classA.start();

        ClassC classC = new ClassC(phone);
        classC.start();

        Thread.sleep(5000);


        Runnable b2 = new ClassB(phone);
        Thread b2Thread = new Thread(b2);
        b2Thread.setName("ClassB");
        b2Thread.start();


        Thread.sleep(20000);
        Runnable bossRunnable = () -> {
            System.out.println("Boss started");
            synchronized (phone) {
                phone.setBossInOffice(true);
                phone.notifyAll();
            }

            System.out.println("Boss finished");
        };
        Thread bossThread = new Thread(bossRunnable);
        bossThread.start();


    }


}
