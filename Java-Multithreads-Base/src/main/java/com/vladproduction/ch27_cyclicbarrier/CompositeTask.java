package com.vladproduction.ch27_cyclicbarrier;

import java.util.List;

/**
 * <S extends Task> this is type of subtask from a complicated task
 * */
public abstract class CompositeTask<S extends Task> extends Task {

    private final List<S> subtasks;

    public CompositeTask(long id, List<S> subtasks) {
        super(id);
        this.subtasks = subtasks;
    }

    @Override
    public void perform() {
        this.subtasks.forEach(this::perform);
    }

    protected abstract void perform(S subtask);



}
