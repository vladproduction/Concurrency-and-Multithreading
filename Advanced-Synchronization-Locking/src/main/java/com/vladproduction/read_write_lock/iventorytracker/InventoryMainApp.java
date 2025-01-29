package com.vladproduction.read_write_lock.iventorytracker;

public class InventoryMainApp {
    public static void main(String[] args) {

        Inventory inventory = new Inventory(50, 100);

        // Creating reader threads (simulating inventory checks)
        for (int i = 0; i < 5; i++) {
            new Thread(new InventoryChecker(inventory), "Checker-" + i).start();
        }

        // Creating writer threads (simulating adding and removing items)
        for (int i = 0; i < 3; i++) {
            new Thread(new InventoryUpdater(inventory, i), "Updater-" + i).start();
        }

    }
}
