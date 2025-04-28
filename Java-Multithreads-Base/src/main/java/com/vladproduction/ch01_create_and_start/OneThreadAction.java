package com.vladproduction.ch01_create_and_start;

public class OneThreadAction {
    public static void main(String[] args) {
        //p - participant; 100 - distance (m); 11-8 speed (m/sec)
        Player p1 = new Player("P1",100,11);
        Player p2 = new Player("P2",100,10);
        Player p3 = new Player("P3",100,9);
        Player p4 = new Player("P4",100,8);

        long startTime = System.currentTimeMillis();
        System.out.println("startTime = " + startTime);
        p1.makeAction();
        p2.makeAction();
        p3.makeAction();
        p4.makeAction();
        long finishTime = System.currentTimeMillis();
        System.out.println("finishTime = " + finishTime);
        //how long time does it take to makeAction for each p
        long time = finishTime-startTime;
        System.out.println("time = " + time);

//        -the main() -  thread in action by input some logic;
//        -step by step is going;


    }
}

class Player {

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
