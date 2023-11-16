package vladproduction.app02_race_condition;

public class IncrementThread extends Thread{

    private final MyData myData;
    private final int n;

    public IncrementThread(MyData myData, int n) {
        this.myData = myData;
        this.n = n;
    }

    @Override
    public void run(){
        System.out.println("Increment START");
        for (int i = 0; i < n; i++) {
            myData.increment();
        }
        System.out.println("Increment FINISH");
    }

}
