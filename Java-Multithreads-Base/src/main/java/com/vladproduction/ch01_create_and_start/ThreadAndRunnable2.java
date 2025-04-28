package com.vladproduction.ch01_create_and_start;

public class ThreadAndRunnable2 {

    public static void main(String[] args) {
        Phone phone = new Phone();
        //Phone phoneB = new Phone();

        A a = new A(phone);
        a.start();


        Runnable runnable = new B(phone);
        Thread bThread = new Thread(runnable);
        bThread.setName("B");
        bThread.start();

//        System.out.println("--------------------------");
//        while(true){
//            System.out.println(bThread.getState());
//            System.out.println(bThread.getName());
//        }


    }


}

class Phone {

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

class A  extends Thread {
    private final Phone phone;

    public A(Phone phone) {
        this.phone = phone;
        setName("A");
    }

    public void run(){
        System.out.println("A started");
        phone.makeCall(5000);
        System.out.println("A finished");
    }

}

class B implements Runnable {
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

