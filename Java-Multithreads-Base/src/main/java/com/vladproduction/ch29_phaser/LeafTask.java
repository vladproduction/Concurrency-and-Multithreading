package com.vladproduction.ch29_phaser;

import java.util.concurrent.Phaser;

public class LeafTask extends AbstractLeafTask{

    public LeafTask(long id, long secondDuration, Phaser phaser) {
        super(id, secondDuration, phaser);
    }

    @Override
    protected void onFinish(Phaser phaser) {
        phaser.arriveAndAwaitAdvance();
    }
}
