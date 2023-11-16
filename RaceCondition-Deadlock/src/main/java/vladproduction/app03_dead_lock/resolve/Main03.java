package vladproduction.app03_dead_lock.resolve;

public class Main03 {
    public static void main(String[] args) {

        Main03 main03 = new Main03();
        main03.runAction();

    }

    public static String xBox = "xBox";
    public static String drums = "drums";

    private void runAction() {

        John john = new John();
        Bob bob = new Bob();
        john.start();
        bob.start();

    }
}
