package vladproduction.com.create_and_start.thread_vs_runnable;

public class ObjectAtThread implements Runnable{
//    @Override
//    public void run() {
//        System.out.println("Thread-2: start");
//        for (int i = 0; i < Integer.MAX_VALUE; i++) {
//            if(i%10_000_000 == 0){
//                System.out.println("Thread-2: amount = " + i);
//
//                if(Thread.interrupted()){
//                    System.out.println("Thread-2 has been interrupted");
//                    break;
//                }
//            }
//        }
//    }


    @Override
    public void run() {
        System.out.println("Thread-2: start");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println("Thread-2 has been interrupted");
                Thread.currentThread().interrupt();
            }
            System.out.println("Thread-2: amount = " + i);
        }
    }
}
