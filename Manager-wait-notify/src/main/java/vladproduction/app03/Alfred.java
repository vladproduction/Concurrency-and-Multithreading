package vladproduction.app03;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Alfred extends Thread{

    private Computer3 computer3;
    private static final int ALFRED_WORKING = 5;

    public Alfred(Computer3 computer3) {
        this.computer3 = computer3;
        setName("Alfred(thread)");
    }

    @Override
    public void run() {
        out.println("Alfred STARTED");
        synchronized (computer3){
            out.println("\tAlfred took " + computer3.getNameComputer());
            while (!computer3.isProfileTask()){
                try {
                    out.println("\tAlfred give back computer, because his profile task status is: " +
                            "("+ computer3.isProfileTask()+")");
                    computer3.wait();
                    out.println("\tAlfred took "+ computer3.getNameComputer()+
                            ", he has profile task with status:(true) and ready to work now...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                computer3.usingComputer();
                SECONDS.sleep(ALFRED_WORKING);
                out.println("\tAlfred give back computer, because he done for the moment");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        out.println("Alfred FINISHED");
    }
}
