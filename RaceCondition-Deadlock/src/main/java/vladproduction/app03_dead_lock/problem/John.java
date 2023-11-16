package vladproduction.app03_dead_lock.problem;

public class John extends Thread {

    @Override
    public void run() {
        System.out.println("John started");
        synchronized (Main03.drums){

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("\tJohn=drums");
            synchronized (Main03.xBox){
                System.out.println("\tJohn=xBox");
            }
        }
        System.out.println("John finished");
    }
}
