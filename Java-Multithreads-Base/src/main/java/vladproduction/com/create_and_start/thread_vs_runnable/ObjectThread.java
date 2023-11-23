package vladproduction.com.create_and_start.thread_vs_runnable;

public class ObjectThread extends Thread{
    @Override
    public void run() {
        System.out.println("Thread-1: start");
        for (int i = 0; i < 5; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread been interrupted");
                Thread.currentThread().interrupt();
            }
            System.out.println("Thread-1: amount = " + i);
        }
    }
}
