package com.vladproduction.create_and_start.app01;

public class Player {

    private String playerName;
    private int distance;
    private int speed;

    public Player(String playerName, int distance, int speed) {
        this.playerName = playerName;
        this.distance = distance;
        this.speed = speed;
    }

    public Player() {
    }

    public void makeAction(){
        System.out.println("\tSTART action "+playerName);

        //time in milliseconds:
        //long timeToAction = distance/speed;
        //time in seconds: ( )*1000
        long timeToAction = (distance/speed)*1000; // (100m / 10m=1sec) = timeBySpeed
        long playerStartTime = System.currentTimeMillis();
        //smth like dilay here:
        //60
        //11-40-00
        //11-40-10
        //t=10 --> 10<60 ? --> yes --> go to while
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
