package com.vladproduction.ch27_cyclicbarrier;

import java.util.List;

public final class Subtask extends CompositeTask<LeafTask>{

    public Subtask(long id, List<LeafTask> leafTasks) {
        super(id, leafTasks);
    }

    @Override
    protected void perform(LeafTask leafTask) {
        leafTask.perform();
    }
}
