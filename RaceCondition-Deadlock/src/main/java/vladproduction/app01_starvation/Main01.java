package vladproduction.app01_starvation;

public class Main01 {
    public static void main(String[] args) {
        Main01 main01 = new Main01();
        main01.runAction2();
    }

    private void runAction2() {
        //starvation: show the infinity loop;
        ClassThreadA threadA = new ClassThreadA();
        ClassThreadB threadB = new ClassThreadB();

        threadA.setPriority(Thread.MAX_PRIORITY); //or we can just set int value = 1-10
        threadA.start();
        threadB.setPriority(5);
        threadB.start();

    }

    private void runAction() {
        ClassThreadA threadA = new ClassThreadA();
        ClassThreadB threadB = new ClassThreadB();
        //just methods:
        threadA.run(); //ClassThreadA Wed Jul 26 13:49:38 EEST 2023
        threadB.run(); //ClassThreadB Wed Jul 26 13:49:38 EEST 2023

        System.out.println("---------------------------");
        threadA.start(); //ClassThreadA Wed Jul 26 13:52:21 EEST 2023
        threadB.start(); //ClassThreadB Wed Jul 26 13:52:21 EEST 2023
    }
}
