package vladproduction.app02;

import static java.lang.System.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Bob extends Thread {

    private final Computer2 computer2;
    private static final int BOB_WORKING = 4;

    public Bob(Computer2 computer2) {
        this.computer2 = computer2;
        setName("Bob()thread");
    }

    @Override
    public void run() {
        out.println("Bob STARTED");
        synchronized (computer2) {
            out.println("\tBob get " + computer2.getNameComputer());
            try {
                computer2.usingComputer();
                SECONDS.sleep(BOB_WORKING);
                out.println("\tBob give back computer, because he done for the moment");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        out.println("Bob FINISHED");
    }
}
