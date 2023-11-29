package com.vladproduction.multiThreadPool;

public class Main {
    public static void main(String[] args) throws Exception {

//        MultiThreadPool multiThreadPool = new MultiThreadPool(); //does not work correct
//        multiThreadPool.starter(6);

        MultiThreadPool_2 multiThreadPool2 = new MultiThreadPool_2(); //work but need to be refactored
        multiThreadPool2.starter();

    }
}
