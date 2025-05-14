package com.vladproduction.concurrency_design_patterns.thread_per_message_pattern;

public class ThreadPerMessagePatterExample {
    public static void main(String[] args) {

        String[] messages = {"Message 1", "Message 2", "Message 3", "Message 4" , "Message 5"};

        for (String message : messages) {
            new Thread(new PrintRequestHandler(message)).start();
        }

    }
}
