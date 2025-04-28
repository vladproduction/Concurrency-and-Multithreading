package com.vladproduction.ch25_poll_objects;

import java.util.Objects;

public final class Connection {

    private final long id;
    private boolean autoCommit;

    public Connection(long id, boolean autoCommit) {
        this.id = id;
        this.autoCommit = autoCommit;
    }

    public long getId() {
        return id;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Connection other = (Connection) obj;
        return id == other.id && autoCommit == other.autoCommit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, autoCommit);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [id = " + id + ", autoCommit = " + autoCommit +']';
    }
}



















