package com.vladproduction.ch28_exchanger;

import java.util.concurrent.TimeUnit;

public class ExchangedObjectFactory {

    private long nextId;

    public ExchangedObject create(){
        try{
            TimeUnit.SECONDS.sleep(2);
            return new ExchangedObject(nextId++);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

}
