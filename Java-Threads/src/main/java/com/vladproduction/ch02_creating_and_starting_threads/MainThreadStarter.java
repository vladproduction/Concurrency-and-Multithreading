package com.vladproduction.ch02_creating_and_starting_threads;

public class MainThreadStarter {

    public static void main(String[] args) {

        FirstThread f1 = new FirstThread();
        FirstThread f2 = new FirstThread();
        FirstThread f3 = new FirstThread();

        FirstThread[] firstThreads = {f1, f2, f3};

        starterThreads(firstThreads);

    }

    private static void starterThreads(FirstThread[] threads) {
        System.out.println("FirstThreads is started simultaneously");
        for (FirstThread thread : threads) {
            thread.start();
        }
    }

    static class FirstThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i <= 5; i++) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

    }

}
