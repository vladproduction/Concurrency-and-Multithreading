package vladproduction.app03;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Daniel extends Thread{

    private Computer3 computer3;
    private static final int DANIEL_WORKING = 5;

    public Daniel(Computer3 computer3) {
        this.computer3 = computer3;
        setName("Daniel(thread)");
    }

    @Override
    public void run() {
        out.println("Daniel STARTED");
        synchronized (computer3){
            out.println("\tDaniel took " + computer3.getNameComputer());
            while (!computer3.isProfileTask()){
                try {
                    out.println("\tDaniel give back computer, because his profile task status is: " +
                            "("+ computer3.isProfileTask()+")");
                    computer3.wait();
                    out.println("\tDaniel took "+ computer3.getNameComputer()+
                            ", he has profile task with status:(true) and ready to work now...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                computer3.usingComputer();
                SECONDS.sleep(DANIEL_WORKING);
                out.println("\tDaniel give back computer, because he done for the moment");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        out.println("Daniel FINISHED");
    }
}
