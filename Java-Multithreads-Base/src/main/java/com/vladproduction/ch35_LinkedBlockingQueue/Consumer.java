package com.vladproduction.ch35_LinkedBlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Consumer class that removes items from the queue
 */
public class Consumer {

    /**
     * Consume items from the queue
     * @param queue The queue to take items from
     * @param stopFlag Signal to stop consuming
     */
    public static void consume(LinkedBlockingQueue<String> queue, AtomicBoolean stopFlag) throws InterruptedException {
        while (!stopFlag.get()) {
            // Try to take an item from the queue with a timeout
            String item = queue.poll(100, TimeUnit.MILLISECONDS);

            if (item != null) {
                Logger.log("Consumed: " + item + " - Queue size: " + queue.size());

                // Process the item (simulate some work)
                Thread.sleep(500);
            }
        }

        // Consume any remaining items in the queue
        String item;
        while ((item = queue.poll()) != null) {
            Logger.log("Cleaning up: " + item);
        }

        Logger.log("Consumer finished");
    }

}
