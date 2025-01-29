package com.vladproduction.read_write_lock.iventorytracker;

public class InventoryChecker implements Runnable {

    private final Inventory inventory;

    public InventoryChecker(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            //read logic
            System.out.println(Thread.currentThread().getName() + " checked inventory and found: "
                    + inventory.getItemCount() + " items.");
            //get transaction history
            System.out.println(Thread.currentThread().getName() + " Transaction History: "
                    + inventory.getTransactionHistory());
            //sleep for a while:
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
