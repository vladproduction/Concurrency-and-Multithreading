package com.vladproduction.ch0_training_tasks_core;

import java.io.IOException;

public class ClosableExceptionTest {
    public static void main(String[] args) {
        try (SomeResource r = new SomeResource()) {
            throw new RuntimeException("RuntimeException");
        } catch (IOException e) {
            System.out.println("In the catch block");
        }
    }

    static class SomeResource implements java.io.Closeable {
        public void close() throws IOException {
            System.out.print("Closing resource ");
            throw new IOException("Close Exception");
        }
    }
}

