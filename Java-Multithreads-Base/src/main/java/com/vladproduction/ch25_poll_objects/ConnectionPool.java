package com.vladproduction.ch25_poll_objects;

import java.util.function.Supplier;

public final class ConnectionPool extends AbstractPool<Connection> {

    public ConnectionPool(int size){
        super(new ConnectionSupplier(), size);
    }

    @Override
    protected void cleanObject(Connection connection) {
        connection.setAutoCommit(true);
    }

    private static final class ConnectionSupplier implements Supplier<Connection> {

        private long nextConnectionId;

        @Override
        public Connection get() {
            return new Connection(nextConnectionId++, true);
        }
    }
}
