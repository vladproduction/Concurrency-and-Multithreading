package com.vladproduction.ch27_cyclicbarrier;

import java.util.List;

public final class MainTask extends CompositeTask<Subtask>{

    public MainTask(long id, List<Subtask> subtasks) {
        super(id, subtasks);
    }

    @Override
    protected void perform(Subtask subtask) {
        new Thread(subtask::perform).start();
    }
}
