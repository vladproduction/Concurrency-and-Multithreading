package com.vladproduction.synchronized_key_word.app01;

public class Main02 {
    public static void main(String[] args) {
        Main02 main02 = new Main02();
        main02.runAction();
    }

    private void runAction() {
        //StringBuilder sb = new StringBuilder(); //not synchronized
        StringBuffer sb = new StringBuffer(); //synchronized
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                sb.append("aaa bbb ccc aaa bbb ccc aaa bbb ccc aaa bbb ccc aaa bbb ccc aaa bbb ccc aaa bbb ccc " +
                                "aaa bbb ccc aaa bbb ccc aaa bbb ccc aaa bbb ccc aaa bbb ccc aaa bbb ccc " +
                                "aaa bbb ccc aaa bbb ccc aaa bbb ccc aaa bbb ccc aaa bbb ccc aaa bbb ccc ")
                        .append(Thread.currentThread().getName())
                        .append("\n");

                System.out.println(sb);
            }
        };


        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);

        thread1.start();
        thread2.start();
        thread3.start();

    }
}
