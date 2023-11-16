package vladproduction.app01;

import static java.util.concurrent.TimeUnit.SECONDS;

public class PersonB extends Thread {

    private final Computer computer;
    private final String namePerson;
    private static final int DURATION_BEFORE_START_WORK = 5;

    public String getNamePerson() {
        return namePerson;
    }

    public PersonB(String namePerson, Computer computer) {
        this.namePerson = namePerson;
        this.computer = computer;
    }

    @Override
    public void run() {
        System.out.println("PersonB started");
        synchronized (computer){
            System.out.println("\tPersonB took " + computer.getNameComputer());
            try {
                SECONDS.sleep(DURATION_BEFORE_START_WORK);
                computer.computerInUse();
                System.out.println("\tPersonB bring back " + computer.getNameComputer());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("PersonB finished");
    }
}
