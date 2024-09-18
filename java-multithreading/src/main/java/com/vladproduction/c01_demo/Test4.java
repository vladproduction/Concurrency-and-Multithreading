package com.vladproduction.c01_demo;

public class Test4 {
    public static void main(String[] args) {

        System.out.println("Main start");

        Thread thread = new Thread(new Runner());
        thread.start();

        System.out.println("Main finish");


    }

    static class Runner implements Runnable{
        @Override
        public void run() {
            System.out.println("Runner start!!!");
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Runner is run: " + i);

            }
            System.out.println("Runner stop!!!");
        }
    }
}
