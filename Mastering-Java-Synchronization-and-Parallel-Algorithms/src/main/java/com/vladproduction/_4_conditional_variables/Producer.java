package com.vladproduction._4_conditional_variables;

class Producer extends Thread {
    private SharedResource resource;

    Producer(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.produce(i);
        }
    }
}
