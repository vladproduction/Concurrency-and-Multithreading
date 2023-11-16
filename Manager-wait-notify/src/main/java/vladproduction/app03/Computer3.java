package vladproduction.app03;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Computer3 {

    private boolean profileTask = false;
    private String nameComputer;
    private static final long DURATION_OF_USING_COMPUTER = 3;

    public Computer3(String nameComputer) {
        this.nameComputer = nameComputer;
    }

    public boolean isProfileTask() {
        return profileTask;
    }

    public void setProfileTask(boolean profileTask) {
        this.profileTask = profileTask;
    }

    public void setNameComputer(String nameComputer) {
        this.nameComputer = nameComputer;
    }

    public String getNameComputer() {
        return nameComputer;
    }

    public synchronized void usingComputer(){
        out.println("\t\t-usingComputer by "+Thread.currentThread().getName());
        try {
            SECONDS.sleep(DURATION_OF_USING_COMPUTER);
            out.println("\t\t-computer: "+getNameComputer()+" <IS FREE NOW>; "
                    +Thread.currentThread().getName()+" finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
