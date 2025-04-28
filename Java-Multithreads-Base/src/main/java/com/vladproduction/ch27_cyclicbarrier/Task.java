package com.vladproduction.ch27_cyclicbarrier;

public abstract class Task {

    private final long id;

    public Task(long id) {
        this.id = id;
    }

    public abstract void perform();

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [id = " + id + "]";
    }
}
