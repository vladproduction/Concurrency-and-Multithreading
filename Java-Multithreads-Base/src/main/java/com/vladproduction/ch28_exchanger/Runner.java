package com.vladproduction.ch28_exchanger;

import java.util.Queue;
import java.util.concurrent.Exchanger;

public class Runner {
    public static void main(String[] args) {

        Exchanger<Queue<ExchangedObject>> exchanger = new Exchanger<>();

        ExchangedObjectFactory factory = new ExchangedObjectFactory();
        int producedObjectCount = 3;

        ProducingTask producingTask = new ProducingTask(exchanger, factory, producedObjectCount);
        ConsumingTask consumingTask = new ConsumingTask(exchanger);

        new Thread(producingTask).start();
        new Thread(consumingTask).start();


    }
}
