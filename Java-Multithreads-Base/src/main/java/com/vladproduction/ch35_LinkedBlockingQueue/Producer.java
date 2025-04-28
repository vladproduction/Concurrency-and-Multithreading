package com.vladproduction.ch35_LinkedBlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Producer class that adds items to the queue
 */
public class Producer {

    /**
     * Produce items and add them to the queue
     * @param queue The queue to add items to
     * @param stopFlag Signal to stop producing
     */
    public static void produce(LinkedBlockingQueue<String> queue, AtomicBoolean stopFlag) throws InterruptedException {
        int itemCount = 1;

        while (!stopFlag.get()) {
            String item = "Item-" + itemCount++;

            // Try to add the item to the queue with a timeout
            boolean added = queue.offer(item, 100, TimeUnit.MILLISECONDS);

            if (added) {
                Logger.log("Produced: " + item + " - Queue size: " + queue.size());
            } else {
                Logger.log("Queue full, couldn't add: " + item);
            }

            // Simulate some work
            Thread.sleep(200);
        }

        Logger.log("Producer finished");
    }

}
