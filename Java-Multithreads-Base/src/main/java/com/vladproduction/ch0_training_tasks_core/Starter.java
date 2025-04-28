package com.vladproduction.ch0_training_tasks_core;

public class Starter implements Runnable {

    void go(long id){
        System.out.println(id);
    }

    public static void main(String[] args) {

        //code inserted:
//        new Starter().run(); //1
//        new Starter().start(); //compile error
//        new Thread(new Starter()); // (empty)
//        new Thread(new Starter()).run(); //1
        new Thread(new Starter()).start();//24

    }

    @Override
    public void run() {
        go(Thread.currentThread().getId());
    }
}
