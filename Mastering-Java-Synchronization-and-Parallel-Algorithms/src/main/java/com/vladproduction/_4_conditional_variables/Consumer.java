package com.vladproduction._4_conditional_variables;

class Consumer extends Thread {
    private SharedResource resource;

    Consumer(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.consume();
        }
    }
}
