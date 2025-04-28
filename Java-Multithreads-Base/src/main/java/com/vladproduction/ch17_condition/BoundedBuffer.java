package com.vladproduction.ch17_condition;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public final class BoundedBuffer<T> {

    private final T[] elements;
    private int size;

    //need to have lock to avoid race condition
    private final Lock lock;

    //need to condition for our while loops
    private final Condition condition;

    @SuppressWarnings("unchecked")
    public BoundedBuffer(int capacity) {
        elements = (T[]) new Object[capacity];
        lock = new ReentrantLock();
        condition = lock.newCondition(); // get a condition object for the lock
    }

    //method to check if the buffer is full
    //and we add lock to avoid race condition
    public boolean isFull() {
        lock.lock();
        try {
            return size == elements.length;
        }finally {
            lock.unlock();
        }
    }

    //method to check if the buffer is empty
    //and we add lock to avoid race condition
    public boolean isEmpty() {
        lock.lock();
        try{
            return size == 0;
        }finally {
            lock.unlock();
        }
    }

    //method to add an element to the buffer
    //and we add lock to avoid race condition
    //have to place condition in while loop for waiting if the buffer is full
    //after we add element to the buffer we signal all threads with waiting for space to become available
    //also add logging to see element were placed in the buffer and result buffer now like this
    public void put(T element) {
        lock.lock();
        try {
            while (isFull()) {
                condition.await();
            }
            elements[size] = element;
            size++;
            System.out.printf("%s was placed in the buffer. Result buffer: %s%n", element, this);
            condition.signalAll();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        finally {
            lock.unlock();
        }
    }

    //method to get an element from the buffer
    //and we add lock to avoid race condition
    //we have to have a condition while buffer is empty, we are waiting
    //and add signal all threads (which are been waiting) to available for put some elements to the buffer
    //also add logging to see element were taken from the buffer and result buffer now like this
    public T take() {
        lock.lock();
        try{
            while (isEmpty()) {
                condition.await();
            }
            T element = elements[size - 1];
            elements[size - 1] = null;
            size--;
            System.out.printf("%s was taken from the buffer. Result buffer: %s%n", element, this);
            condition.signalAll();
            return element;
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        finally {
            lock.unlock();
        }
    }

    //overridden toString method
    //we have to add lock to avoid race condition for toString() method also
    @Override
    public String toString() {
        lock.lock();
        try{
            return "{" + Arrays.stream(elements, 0, size).map(Objects::toString).collect(Collectors.joining(", ")) + "}";
        }finally {
            lock.unlock();
        }
    }

}
