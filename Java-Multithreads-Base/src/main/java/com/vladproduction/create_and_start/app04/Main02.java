package com.vladproduction.create_and_start.app04;

public class Main02 {
    public static void main(String[] args) {
        Thread thread = new Thread(){
            @Override
            public void run(){
                System.out.println(Thread.currentThread().getName());
            }
        };
        thread.start();
        //thread.run(); //main
    }
}
