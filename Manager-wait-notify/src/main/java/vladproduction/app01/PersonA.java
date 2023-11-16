package vladproduction.app01;

import static java.util.concurrent.TimeUnit.SECONDS;

public class PersonA extends Thread{

    private final Computer computer;
    private final String namePerson;
    private static final int DURATION_BEFORE_START_WORK = 2;

    public String getNamePerson() {
        return namePerson;
    }

    public PersonA(String namePerson, Computer computer) {
        this.namePerson = namePerson;
        this.computer = computer;
    }

    @Override
    public void run() {
        System.out.println("PersonA started");
        synchronized (computer){
            System.out.println("\tPersonA took " + computer.getNameComputer());
            try {
                SECONDS.sleep(DURATION_BEFORE_START_WORK);
                computer.computerInUse();
                System.out.println("\tPersonA bring back " + computer.getNameComputer());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("PersonA finished");
    }
}
