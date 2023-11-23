package vladproduction.com.create_and_start.thread_vs_runnable;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.runAction();
    }

    private void runAction() {
        Thread thread1 = new ObjectThread();
        thread1.start();
        Thread thread2 = new Thread(new ObjectAtThread());
        thread2.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread1.interrupt();
        thread2.interrupt();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Main thread will continue");
    }
}
