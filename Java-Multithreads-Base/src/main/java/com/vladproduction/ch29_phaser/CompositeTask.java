package com.vladproduction.ch29_phaser;

import java.util.List;

public abstract class CompositeTask<S extends Task> extends Task {

    private final List<S> subtasks;

    public CompositeTask(long id, List<S> subtasks) {
        super(id);
        this.subtasks = subtasks;
    }

    @Override
    public void perform() {
        subtasks.forEach(this::perform);
    }

    protected abstract void perform(S subtask);

}
