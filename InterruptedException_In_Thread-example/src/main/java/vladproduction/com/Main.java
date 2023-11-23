package vladproduction.com;

public class Main {
    public static void main(String[] args) {

        MyThread myThread = new MyThread();
        myThread.start();
        pause(10000);
        System.out.println("Main is going to interrupt myThread");
        myThread.interrupt();
        System.out.println("Main- FINISH");
    }

    private static void pause(long delay){
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < delay){
        }
    }
}
