package vladproduction.app04_Runnable;

public class Main04 {
    public static void main(String[] args) {
       Main04 main04 = new Main04();
       main04.runAction();
    }

    private void runAction(){

        //class--------------
        ClassA classA = new ClassA();
        Thread thread = new Thread(classA);
        thread.start();

        //anonymous class--------------
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("r2: anonymous");
            }
        };
        new Thread(r2).start();

        //lambda--------------
        Runnable r3 = () ->{
            System.out.println("r3: lambda");
        };
        new Thread(r3).start();

        //lambda + stream--------------
        new Thread(()-> System.out.println("r4: lambda + stream")).start();

    }
}
