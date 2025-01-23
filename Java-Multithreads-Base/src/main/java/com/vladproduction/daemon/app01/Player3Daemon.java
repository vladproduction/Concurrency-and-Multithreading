package com.vladproduction.daemon.app01;

public class Player3Daemon extends Thread{

    private final String playerName;
    private final int distance;
    private final int speed;

    public Player3Daemon(String playerName, int distance, int speed) {
        this.playerName = playerName;
        this.distance = distance;
        this.speed = speed;
    }

    @Override
    public void run() {makeAction();}

    public void makeAction(){
        System.out.println("\tSTART action "+playerName);

        long timeToAction = (distance/speed)* 1000L;
        long playerStartTime = System.currentTimeMillis();
        System.out.println("\t\texpected timeToAction: "+timeToAction);
        while ((System.currentTimeMillis() - playerStartTime) < timeToAction){
        }

        System.out.println("\tFINISH action"+playerName);
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", distance=" + distance +
                ", speed=" + speed +
                '}';
    }
}
