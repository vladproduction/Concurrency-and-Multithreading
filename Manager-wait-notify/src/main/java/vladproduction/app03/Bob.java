package vladproduction.app03;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Bob extends Thread {

    private Computer3 computer3;
    private static final int BOB_WORKING = 4;

    public Bob(Computer3 computer3) {
        this.computer3 = computer3;
        setName("Bob()thread");
    }

    @Override
    public void run() {
        out.println("Bob STARTED");
        synchronized (computer3) {
            out.println("\tBob get " + computer3.getNameComputer());
            try {
                computer3.usingComputer();
                SECONDS.sleep(BOB_WORKING);
                out.println("\tBob give back computer, because he done for the moment");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        out.println("Bob FINISHED");
    }
}
