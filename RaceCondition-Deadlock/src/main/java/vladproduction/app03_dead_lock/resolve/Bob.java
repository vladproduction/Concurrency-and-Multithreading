package vladproduction.app03_dead_lock.resolve;

public class Bob extends Thread{

    @Override
    public void run() {
        System.out.println("Bob started");
        synchronized (Main03.xBox){

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("\tBob=xBox");
            synchronized (Main03.drums){
                System.out.println("\tBob=drums");
            }
        }
        System.out.println("Bob finished");
    }
}
