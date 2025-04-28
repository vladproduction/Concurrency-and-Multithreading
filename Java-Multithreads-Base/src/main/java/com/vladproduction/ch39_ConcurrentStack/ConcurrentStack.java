package com.vladproduction.ch39_ConcurrentStack;

import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A lock-free concurrent stack implementation based on Treiber's stack design
 * using AtomicReference for thread-safe operations.
 */
public class ConcurrentStack<T> {

    // The top of the stack is an AtomicReference to the first node
    private final AtomicReference<Node<T>> top = new AtomicReference<>(null);

    // Node class represents elements in the stack
    private static class Node<T> {
        // The data stored in this node
        private final T data;
        // Reference to the next node in the stack
        private volatile Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Push an item onto the top of the stack.
     * Uses Compare-And-Set for thread safety without locks.
     */
    public void push(T item) {
        Node<T> newHead = new Node<>(item);
        Node<T> oldHead;

        do {
            // Get the current top of stack
            oldHead = top.get();
            // Point the new node to the current top
            newHead.next = oldHead;
            // Try to replace the top with the new node atomically
            // If another thread modified the stack, the CAS will fail and we try again
        } while (!top.compareAndSet(oldHead, newHead));
    }

    /**
     * Pop an item from the top of the stack.
     * Uses Compare-And-Set for thread safety without locks.
     *
     * @return The item at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public T pop() {
        Node<T> oldHead;
        Node<T> newHead;

        do {
            // Get the current top of stack
            oldHead = top.get();

            // Check if stack is empty
            if (oldHead == null) {
                throw new EmptyStackException();
            }

            // The new head will be the next node
            newHead = oldHead.next;
            // Try to update the top atomically
            // If another thread modified the stack, the CAS will fail and we try again
        } while (!top.compareAndSet(oldHead, newHead));

        // Return the data from the old head
        return oldHead.data;
    }

    /**
     * Peek at the top element of the stack without removing it.
     *
     * @return The item at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public T peek() {
        Node<T> head = top.get();
        if (head == null) {
            throw new EmptyStackException();
        }
        return head.data;
    }

    /**
     * Check if the stack is empty.
     *
     * @return true if the stack contains no elements
     */
    public boolean isEmpty() {
        return top.get() == null;
    }

}
