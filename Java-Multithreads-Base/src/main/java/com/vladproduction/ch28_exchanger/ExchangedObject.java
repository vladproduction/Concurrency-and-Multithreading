package com.vladproduction.ch28_exchanger;

public final class ExchangedObject {

    private final long id;

    public ExchangedObject(final long id){
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [id = " + id + "]";
    }
}
