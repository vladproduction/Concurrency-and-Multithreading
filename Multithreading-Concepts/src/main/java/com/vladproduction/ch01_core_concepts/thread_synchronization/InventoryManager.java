package com.vladproduction.ch01_core_concepts.thread_synchronization;

import java.util.ArrayList;
import java.util.List;

// Example 3: Synchronized Block (more granular than method-level synchronization)
public class InventoryManager {

    private final List<Product> products = new ArrayList<>();
    private int productIdCounter = 0;

    static class Product {
        private final int id;
        private final String name;
        private int quantity;

        public Product(int id, String name, int quantity) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "Product{id=" + id + ", name='" + name + "', quantity=" + quantity + "}";
        }
    }

    // Demonstrate synchronized block for better performance
    // Only synchronize the critical section, not the entire method
    public void addProduct(String name, int quantity) {
        // This part doesn't require synchronization
        System.out.println(Thread.currentThread().getName() +
                " preparing to add product: " + name);

        // Critical section that needs synchronization
        synchronized (products) {
            int newId;
            // Other threads can't modify productIdCounter here
            newId = ++productIdCounter;

            // Simulate some database operation
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            products.add(new Product(newId, name, quantity));
            System.out.println(Thread.currentThread().getName() +
                    " added: " + name + " with ID: " + newId);
        }

        // This part doesn't require synchronization either
        System.out.println(Thread.currentThread().getName() +
                " finished adding product");
    }

    // Syncing on a different object for separate lock
    public void displayProducts() {
        // Thread-safe read if we only read and don't modify the list
        synchronized (products) {
            System.out.println("\nCurrent Inventory:");
            for (Product product : products) {
                System.out.println(product);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        InventoryManager inventory = new InventoryManager();

        // Multiple threads adding products concurrently
        Thread t1 = new Thread(() -> {
            inventory.addProduct("Laptop", 10);
            inventory.addProduct("Mouse", 20);
            inventory.addProduct("Keyboard", 15);
        }, "Warehouse-1");

        Thread t2 = new Thread(() -> {
            inventory.addProduct("Monitor", 5);
            inventory.addProduct("Printer", 3);
            inventory.addProduct("Scanner", 2);
        }, "Warehouse-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayProducts();
    }

}
