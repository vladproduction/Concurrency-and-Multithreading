package com.vladproduction.ch34_ArrayBlockingQueue.app02_multi_producers_multi_consumers;

public class Item {

    private final int id;
    private final int producerId;

    public Item(int id, int producerId) {
        this.id = id;
        this.producerId = producerId;
    }

    public int getId() {
        return id;
    }

    public int getProducerId() {
        return producerId;
    }

    @Override
    public String toString() {

        return String.format("[id = %d, producerId = %d]", id, producerId);
    }
}
