package com.vladproduction.ch28_exchanger;

import java.util.Queue;
import java.util.concurrent.Exchanger;
import java.util.stream.IntStream;

public final class ProducingTask extends ExchangingTask{

    private final ExchangedObjectFactory objectFactory;
    private final int producedObjectCount;

    public ProducingTask(Exchanger<Queue<ExchangedObject>> exchanger, ExchangedObjectFactory objectFactory, int producedObjectCount) {
        super(exchanger);
        this.objectFactory = objectFactory;
        this.producedObjectCount = producedObjectCount;
    }

    @Override
    protected void handle(Queue<ExchangedObject> objects) {
        IntStream.range(0, producedObjectCount)
                .mapToObj(i -> objectFactory.create())
                .peek(object -> System.out.printf("%s is been produced.\n", object))
                .forEach(objects::add);
    }
}
