package com.vladproduction.ch01_create_and_start;



public class ManyThreadsAction {
    public static void main(String[] args) {

        Player2 p1 = new Player2("P1",100,11);
        Player2 p2 = new Player2("P2",100,10);
        Player2 p3 = new Player2("P3",100,9);
        Player2 p4 = new Player2("P4",100,8);

        long startTime = System.currentTimeMillis();
        System.out.println("startTime = " + startTime);

        p1.start();
        p2.start();
        p3.start();
        p4.start();

        long finishTime = System.currentTimeMillis();
        System.out.println("finishTime = " + finishTime);

        long time = finishTime-startTime;
        System.out.println("time = " + time);

//        -class 'Player2' extends Thread, in overridden method run() we implementing makeAction() with logic;
//        -now instances can start(), and we have no order which one start first;

    }

}

class Player2 extends Thread{

    private final String playerName;
    private final int distance;
    private final int speed;

    public Player2(String playerName, int distance, int speed) {
        this.playerName = playerName;
        this.distance = distance;
        this.speed = speed;
    }


    @Override
    public void run() {
        makeAction();
    }

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
