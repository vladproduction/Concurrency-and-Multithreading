package vladproduction.app03_dead_lock.problem;

public class Bob extends Thread{

    @Override
    public void run() {
        System.out.println("Bob started");
        synchronized (Main03.xBox){

            try {
                Thread.sleep(3000);
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
