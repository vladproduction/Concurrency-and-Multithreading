package com.vladproduction.ch0_training.exceptions_try_catch;

public class TryCatchExceptionFlowTesting {
    public static void main(String[] args) {

                try {
                    System.out.print("Try ");
                    throw new Exception();
                } catch (Exception e) {
                    System.out.print("Catch ");
                    System.exit(1);
                } finally {
                    System.out.print("Finally ");
                }
                System.out.println("Done ");

    }
}
