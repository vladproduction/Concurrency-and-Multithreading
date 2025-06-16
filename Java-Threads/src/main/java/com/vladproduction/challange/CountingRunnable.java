package com.vladproduction.challange;

public class CountingRunnable implements Runnable {

    private final Design design;
    protected boolean doStop = false;

    public CountingRunnable(Design design) {
        this.design = design;
    }

    @Override
    public void run() {
        while (!doStop) {
            System.out.println("Design " + design.getName() + " has " + design.getVotes().size() + " votes");
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

}
