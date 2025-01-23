package com.vladproduction.synchronized_key_word.app01;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.runMain2();
    }

    //synchronized block :(this){}
    private static int counter;

    private void runMain2() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    counter = 0;
                    for (int i = 0; i < 5; i++) {
                        System.out.println(Thread.currentThread().getName() + " : " + counter);
                        counter++;

                    }
                }
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
    }

    //synchronized
    private void runMain1() {
        Runnable runnable = new Runnable() {

            @Override
            synchronized public void run() {
                counter = 0;
                for (int i = 0; i < 5; i++) {
                    counter += +1;
                    String nameCurrentThread = Thread.currentThread().getName();
                    System.out.println(nameCurrentThread + " : " + counter);

                }
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();

    }

    //non-synchronized
    private void runMain() {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                counter = 0;
                for (int i = 0; i < 5; i++) {
                    counter += +1;
                    String nameCurrentThread = Thread.currentThread().getName();
                    System.out.println(nameCurrentThread + " : " + counter);

                }
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();

    }
}
