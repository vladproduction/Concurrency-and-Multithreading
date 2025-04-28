package com.vladproduction.ch25_poll_objects;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public abstract class AbstractPool<T> {

    private final List<PoolObject<T>> poolObjects;
    private final Semaphore semaphore;

    public AbstractPool(Supplier<T> objectSupplier, int size) {
        poolObjects = createPollObjects(objectSupplier, size);
        semaphore = new Semaphore(size);

    }

    //method to create List<PoolObject<T>> by Supplier and size
    private static <T> List<PoolObject<T>> createPollObjects(Supplier<T> objectSupplier, int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> objectSupplier.get())
                .map(object -> new PoolObject<>(object, false))
                .toList();
    }

    //method to take objects from a pool
    public final T acquire(){
        semaphore.acquireUninterruptibly();
        return acquireObject();
    }

    //method to find any object among not given objects:
    private synchronized T acquireObject() {
        return poolObjects.stream()
                .filter(poolObject -> !poolObject.isIssued())
                .findFirst()
                .map(AbstractPool::markAsIssued)
                .map(PoolObject::getObject)
                .orElseThrow(IllegalStateException::new);
    }

    //method to mark the object already as given
    private static <T> PoolObject<T> markAsIssued(PoolObject<T> poolObject) {
        poolObject.setIssued(true);
        return poolObject;
    }

    //method to release an object and place it back to pool
    public final void release(T object){
        if(releaseObject(object)){
            semaphore.release();
        }else{
            throw new IllegalStateException("Object is not in the pool\n");
        }
    }

    private synchronized boolean releaseObject(T object){
        return poolObjects.stream()
                .filter(PoolObject::isIssued)
                .filter(poolObject -> Objects.equals(poolObject.getObject(), object))
                .findFirst()
                .map(this::cleanPoolObject)
                .isPresent();
    }

    protected abstract void cleanObject(T object);

    private PoolObject<T> cleanPoolObject(PoolObject<T> poolObject){
        poolObject.setIssued(false);
        cleanObject(poolObject.getObject());
        return poolObject;

    }


    private static final class PoolObject<T> {

        private final T object;
        private boolean issued;

        public PoolObject(T object, boolean issued) {
            this.object = object;
            this.issued = issued;
        }

        public T getObject() {
            return object;
        }

        public boolean isIssued() {
            return issued;
        }

        public void setIssued(boolean issued) {
            this.issued = issued;
        }
    }

}
















