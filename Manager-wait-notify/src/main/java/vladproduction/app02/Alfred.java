package vladproduction.app02;

import static java.lang.System.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Alfred extends Thread{

    private final Computer2 computer2;
    private static final int ALFRED_WORKING = 5;

    public Alfred(Computer2 computer2) {
        this.computer2 = computer2;
        setName("Alfred(thread)");
    }

    @Override
    public void run() {
        out.println("Alfred STARTED");
        synchronized (computer2){
            out.println("Alfred took " + computer2.getNameComputer());
            while (!computer2.isProfileTask()){
                try {
                    out.println("\tAlfred give back computer, because his profile task status is: " +
                            "("+ computer2.isProfileTask()+")");
                    computer2.wait();
                    out.println("\tAlfred took "+computer2.getNameComputer()+
                            ", he has profile task with status:(true) and ready to work now...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                computer2.usingComputer();
                SECONDS.sleep(ALFRED_WORKING);
                out.println("\tAlfred give back computer, because he done for the moment");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        out.println("Alfred FINISHED");
    }
}
