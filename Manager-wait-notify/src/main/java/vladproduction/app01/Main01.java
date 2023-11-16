package vladproduction.app01;

public class Main01 {
    public static void main(String[] args) {
        Main01 main01 = new Main01();
        main01.runAction();
    }

    private void runAction() {
        Computer computer = new Computer("Comp_#1");
        PersonA personA = new PersonA("PersonA",computer);
        PersonB personB = new PersonB("PersonB",computer);
        personA.setName(personA.getNamePerson());
        personB.setName(personB.getNamePerson());

        personA.start();
        personB.start();


    }
}
