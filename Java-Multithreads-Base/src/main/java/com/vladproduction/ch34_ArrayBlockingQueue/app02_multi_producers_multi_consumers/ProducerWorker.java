package com.vladproduction.ch34_ArrayBlockingQueue.app02_multi_producers_multi_consumers;

public class ProducerWorker implements Runnable {

    private final SharedQueueStorage sharedQueueStorage;
    private final int producerId;

    public ProducerWorker(SharedQueueStorage sharedQueueStorage, int producerId) {
        this.sharedQueueStorage = sharedQueueStorage;
        this.producerId = producerId;
    }

    @Override
    public void run() {
        try {
//            for (int i = 1; i <= 5; i++) {
//                Item item = new Item(i, producerId);
//                System.out.printf("Producer %d: Producing item %s\n", producerId, item);
//                sharedQueueStorage.produce(item);
//                System.out.printf("Queue size after producing: %d\n", sharedQueueStorage.getQueueSize());
//                System.out.printf("Total produced: %d\n", sharedQueueStorage.getTotalProduced());
//                System.out.printf("Total consumed: %d\n", sharedQueueStorage.getTotalConsumed());
//                TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
//            }
//            System.out.println("Producer " + producerId + " finished production.");

            for (int i = 1; i <= 5; i++) {
                Item item = new Item(i, producerId);
                System.out.println("Producer " + producerId + " producing: " + item);
                sharedQueueStorage.produce(item);
                System.out.println("Queue size: " + sharedQueueStorage.getQueueSize() +
                        ", Total produced: " + sharedQueueStorage.getTotalProduced() +
                        ", Total consumed: " + sharedQueueStorage.getTotalConsumed());

                // Sleep for a random time between 0 and 1 second
                Thread.sleep((long) (Math.random() * 1000));
            }
            System.out.println("Producer " + producerId + " finished production.");
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Producer: " + producerId + " interrupted: " + e.getMessage());
        }
    }
}
