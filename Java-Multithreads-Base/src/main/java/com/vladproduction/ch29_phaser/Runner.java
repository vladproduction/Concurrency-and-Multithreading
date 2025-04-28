package com.vladproduction.ch29_phaser;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Phaser;

public class Runner {
    public static void main(String[] args) {

        Phaser phaser = new Phaser(3){
            @Override
            protected boolean onAdvance(int phase, int parties) {
                System.out.println();
                System.out.printf("Thread: %s \n", Thread.currentThread().getName());
                System.out.printf("Current phase: %d\n", phase);
                System.out.printf("Current parties: %d\n", parties);
                System.out.println();
                return super.onAdvance(phase, parties);
            }
        };

        LeafTask firstLeafTask = new LeafTask(0, 5, phaser);
        LeafTask secondLeafTask = new LeafTask(1, 3, phaser);
        LastLeafTask firstLastLeafTask = new LastLeafTask(0, 1, phaser);
        Subtask firstSubtask = new Subtask(0, List.of(firstLeafTask, secondLeafTask), firstLastLeafTask);

        LeafTask thirdLeafTask = new LeafTask(2, 6, phaser);
        LastLeafTask secondLastLeafTask = new LastLeafTask(1, 4, phaser);
        Subtask secondSubtask = new Subtask(1, List.of(thirdLeafTask), secondLastLeafTask);

        LastLeafTask thirdLastLeafTask = new LastLeafTask(2, 7, phaser);
        Subtask thirdSubtask = new Subtask(2, Collections.emptyList(), thirdLastLeafTask);

        MainTask mainTask = new MainTask(0, List.of(firstSubtask, secondSubtask, thirdSubtask));
        mainTask.perform();

    }
}
