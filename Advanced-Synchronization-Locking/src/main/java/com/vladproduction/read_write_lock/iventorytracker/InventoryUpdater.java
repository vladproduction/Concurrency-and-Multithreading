package com.vladproduction.read_write_lock.iventorytracker;

import java.util.Random;

public class InventoryUpdater implements Runnable {

    private final Inventory inventory;
    private final int updateId;
    private final Random random;

    public InventoryUpdater(Inventory inventory, int updateId) {
        this.inventory = inventory;
        this.updateId = updateId;
        this.random = new Random();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            // Randomly choose whether to add or remove items
            if (random.nextBoolean()) {
                int quantityToAdd = random.nextInt(1, 21); // Random quantity between 1 and 20
                inventory.addItem(quantityToAdd); // Add random items
            } else {
                int quantityToRemove = random.nextInt(1, 21); // Random quantity between 1 and 20
                inventory.removeItem(quantityToRemove); // Remove random items
            }

            try {
                Thread.sleep(random.nextInt(100, 301)); // Simulate variable time taken to update inventory
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
