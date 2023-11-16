package vladproduction.app02_race_condition;

public class DecrementThread extends Thread {

    private final MyData myData;
    private final int n;

    public DecrementThread(MyData myData, int n) {
        this.myData = myData;
        this.n = n;
    }

    @Override
    public void run() {
        System.out.println("Decrement START");
        for (int i = 0; i < n; i++) {
            myData.decrement();
        }
        System.out.println("Decrement FINISH");
    }

}
