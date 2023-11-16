package vladproduction.com.sleep_join.app02;

public class Player4Join extends Thread{

    private final String playerName;
    private final int distance;
    private final int speed;

    public Player4Join(String playerName, int distance, int speed) {
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


        long timeToAction = (distance/speed)* 1000L; // (100m / 10m=1sec) = timeBySpeed
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
