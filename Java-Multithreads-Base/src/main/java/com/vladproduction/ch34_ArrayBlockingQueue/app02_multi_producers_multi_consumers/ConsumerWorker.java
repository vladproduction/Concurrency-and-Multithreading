package com.vladproduction.ch34_ArrayBlockingQueue.app02_multi_producers_multi_consumers;

public class ConsumerWorker implements Runnable {

    private final SharedQueueStorage sharedQueueStorage;
    private final int consumerId;

    public ConsumerWorker(SharedQueueStorage sharedQueueStorage, int consumerId) {
        this.sharedQueueStorage = sharedQueueStorage;
        this.consumerId = consumerId;
    }

    @Override
    public void run() {
        try{

//            // Each consumer will consume 7-8 items
//            for (int i = 1; i <= 7 + (consumerId % 2); i++) {
//                Item consumedItem = sharedQueueStorage.consume();
//                System.out.printf("Consumer %d: Consumed %s\n", consumerId, consumedItem);
//                System.out.printf("Queue size after consuming: %d\n", sharedQueueStorage.getQueueSize());
//                System.out.printf("Total produced: %d\n", sharedQueueStorage.getTotalProduced());
//                System.out.printf("Total consumed: %d\n", sharedQueueStorage.getTotalConsumed());
//
//                //sleep for a random time between 0 and 2.5 second
//                Thread.sleep((long) (Math.random() * 2500));
//
//            }
//            System.out.println("Consumer " + consumerId + " finished consumption.");

            // Each consumer will consume 7-8 items
            for (int i = 1; i <= 7 + (consumerId % 2); i++) {
                Item item = sharedQueueStorage.consume();
                System.out.println("Consumer " + consumerId + " consumed: " + item);
                System.out.println("Queue size: " + sharedQueueStorage.getQueueSize() +
                        ", Total produced: " + sharedQueueStorage.getTotalProduced() +
                        ", Total consumed: " + sharedQueueStorage.getTotalConsumed());

                // Sleep for a random time between 0 and 1.5 seconds
                Thread.sleep((long) (Math.random() * 1500));
            }
            System.out.println("Consumer " + consumerId + " finished consumption.");

        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.err.println("Consumer: " + consumerId + " interrupted: " + e.getMessage());
        }
    }
}
