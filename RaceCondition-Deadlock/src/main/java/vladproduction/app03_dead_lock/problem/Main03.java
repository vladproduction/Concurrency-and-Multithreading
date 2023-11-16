package vladproduction.app03_dead_lock.problem;

public class Main03 {
    public static void main(String[] args) {

        Main03 main03 = new Main03();
        main03.runAction();

    }

    public static String xBox = "xBox";
    public static String drums = "drums";

    private void runAction() {

        John john = new John(); //5 sec sleep
        Bob bob = new Bob(); //3 sec sleep
        john.start();
        bob.start();

    }
}
