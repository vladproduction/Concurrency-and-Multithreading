package com.vladproduction.ch29_phaser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Subtask extends CompositeTask<AbstractLeafTask>{

    public Subtask(long id, List<LeafTask> leafTasks, LastLeafTask lastLeafTask) {
        super(id, concat(leafTasks, lastLeafTask));
    }

    @Override
    protected void perform(AbstractLeafTask leafTask) {
        leafTask.perform();
    }

    private static List<AbstractLeafTask> concat(List<LeafTask> leafTasks, LastLeafTask lastLeafTask) {
        return Stream.concat(
                leafTasks.stream(),
                Stream.of(lastLeafTask)
        ).toList();

    }

}
