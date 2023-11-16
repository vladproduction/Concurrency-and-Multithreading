package vladproduction.com.daemon.app01;

public class Main03ThreadDaemon {
    public static void main(String[] args) {

        Player3Daemon p1 = new Player3Daemon("P1",100,11);
        Player3Daemon p2 = new Player3Daemon("P2",100,10);
        Player3Daemon p3 = new Player3Daemon("P3",100,9);
        Player3Daemon p4 = new Player3Daemon("P4",100,8);

        p1.setDaemon(false);
        p2.setDaemon(true);
        p3.setDaemon(true);
        p4.setDaemon(true);

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

        /*
        -daemon threads example here;
        p1.setDaemon(false);
        p2.setDaemon(true);
        p3.setDaemon(true);
        p4.setDaemon(true);
        -in that case thread 'p1' finish work and does not waiting for others, -they are true daemons;
        * */


    }
}
