package vladproduction.app03_dead_lock.resolve;

public class John extends Thread {

    @Override
    public void run() {
        System.out.println("John started");
        synchronized (Main03.xBox){

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("\tJohn=xBox");
            synchronized (Main03.drums){
                System.out.println("\tJohn=drums");
            }
        }
        System.out.println("John finished");
    }
}
