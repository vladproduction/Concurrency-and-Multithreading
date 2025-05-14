package com.vladproduction.lock_advanced_synchronization.condition_variable;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedQueue {

    public static final int CAPACITY_OF_SHARED_QUEUE = 5;
    private final Queue<Integer> queue = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition(); //for the producer
    private final Condition notEmpty = lock.newCondition(); //for the consumer

    public void produce(int value){
        lock.lock();
        try{
            while (queue.size() == CAPACITY_OF_SHARED_QUEUE){
                System.out.println("Queue is full, waiting for consumer to consume.");
                notFull.await();//wait until the queue is not full
            }
            queue.add(value);
            System.out.println("Producer produced: " + value);
            notEmpty.signal();//notify the consumer that the queue is not empty
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }finally {
            lock.unlock();
        }
    }

    public void consume(){
        lock.lock();
        try{
            while (queue.isEmpty()){
                System.out.println("Queue is empty. Consumer is waiting for producer to produce.");
                notEmpty.await();
            }
            int value = queue.poll();
            System.out.println("Consumer consumed: " + value);
            notFull.signal();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }finally {
            lock.unlock();
        }
    }

}
