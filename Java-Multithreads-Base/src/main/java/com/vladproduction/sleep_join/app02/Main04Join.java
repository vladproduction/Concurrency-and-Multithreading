package com.vladproduction.sleep_join.app02;


public class Main04Join {
    public static void main(String[] args) throws InterruptedException {

        Player4Join p1 = new Player4Join("P1",100,11);
        Player4Join p2 = new Player4Join("P2",100,10);
        Player4Join p3 = new Player4Join("P3",100,9);
        Player4Join p4 = new Player4Join("P4",100,8);

        long startTime = System.currentTimeMillis();
        System.out.println("startTime = " + startTime);

        p1.start();
        p2.start();
        p3.start();
        p4.start();

        p1.join();
        p2.join();
        p3.join();
        p4.join();
        //show the max value of threads (actually the slowest athlete)

        long finishTime = System.currentTimeMillis();
        System.out.println("finishTime = " + finishTime);

        long time = finishTime-startTime;
        System.out.println("time = " + time);

        //-no guarantee who start first, but finish the fastest;
        //-all participants finishing because of join();

    }
}
