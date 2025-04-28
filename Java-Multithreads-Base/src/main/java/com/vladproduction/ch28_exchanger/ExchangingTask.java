package com.vladproduction.ch28_exchanger;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class ExchangingTask implements Runnable {

    private final Exchanger<Queue<ExchangedObject>> exchanger;
    private Queue<ExchangedObject> objects;

    public ExchangingTask(Exchanger<Queue<ExchangedObject>> exchanger) {
        this.exchanger = exchanger;
        this.objects = new ArrayDeque<>();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            handle(objects);
            exchange();
        }
    }

    protected abstract void handle(Queue<ExchangedObject> objects);

    private void exchange(){
        try{
            objects = exchanger.exchange(objects);
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }

}
