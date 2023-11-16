package vladproduction.app01;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Computer {

    private final String nameComputer;
    private static final long DURATION_OF_USING_COMPUTER = 7;

    public Computer(String nameComputer) {
        this.nameComputer = nameComputer;
    }

    public String getNameComputer() {
        return nameComputer;
    }

    public synchronized void computerInUse(){
        System.out.println("\t\t-computerInUse by "+Thread.currentThread().getName());
        try {
            SECONDS.sleep(DURATION_OF_USING_COMPUTER);
            System.out.println("\t\t-computer: "+getNameComputer()+" <IS FREE NOW>");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
