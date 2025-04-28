package com.vladproduction.ch0_training_tasks_core;

public class TryCatchExceptionTest {
    static String str = "a";

    public static void main(String[] args) {
        new TryCatchExceptionTest().method1();
        System.out.println(str);
    }

    void method1() {
        try {
            method2();
        } catch (Exception e) {
            str += "c";
        }
    }

    void method2() throws Exception {
        method3(); str += "2";
        method3(); str += "2b";
    }

    void method3() throws Exception {
        throw new Exception();
    }
}