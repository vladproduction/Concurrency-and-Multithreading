package vladproduction.com.demo;

public class MyThread extends Thread{


    @Override
    public void run() {
        System.out.println("MyThread-start");
        System.out.println("I`m going to sleep for 1 minute");
        try {
            Thread.sleep(30000);
            System.out.println("I`m back");
            System.out.println("MyThread-finish");
        } catch (InterruptedException e) {
            boolean interrupted = Thread.interrupted();
            boolean interrupted2 = Thread.interrupted();
            System.out.println("interrupted = " + interrupted);
            System.out.println("interrupted2 = " + interrupted2);
            Thread.currentThread().interrupt();

            System.out.println("interrupted3 = " + Thread.interrupted());

            System.out.println("interrupted4 = " + Thread.interrupted());

//            System.out.println("interrupted3 = " + Thread.currentThread().isInterrupted());
//            System.out.println("interrupted4 = " + Thread.currentThread().isInterrupted());


        }
    }
}
