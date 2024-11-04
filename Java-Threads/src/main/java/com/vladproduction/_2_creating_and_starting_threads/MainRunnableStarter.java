package com.vladproduction._2_creating_and_starting_threads;

public class MainRunnableStarter {
    public static void main(String[] args) {

        MyRunnable myRunnable1 = new MyRunnable();

        Thread thread1 = new Thread(myRunnable1);
        Thread thread2 = new Thread(myRunnable1);
        Thread thread3 = new Thread(myRunnable1);
        Thread[] threadsRunnable = {thread1,thread2,thread3};
        starterMyRunnable(threadsRunnable);
    }

    private static void starterMyRunnable(Thread[] threads){
        System.out.println("MyRunnable is started simultaneously");
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
