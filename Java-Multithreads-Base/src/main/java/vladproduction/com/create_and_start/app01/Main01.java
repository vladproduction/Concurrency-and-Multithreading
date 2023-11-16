package vladproduction.com.create_and_start.app01;

public class Main01 {
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
