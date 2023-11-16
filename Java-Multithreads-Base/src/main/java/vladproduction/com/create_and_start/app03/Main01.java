package vladproduction.com.create_and_start.app03;

public class Main01 {

    public static void main(String[] args) {
        Phone phone = new Phone();
        //Phone phoneB = new Phone();

        A a = new A(phone);
        a.start();


        Runnable runnable = new B(phone);
        Thread bThread = new Thread(runnable);
        bThread.setName("B");
        bThread.start();

//        System.out.println("--------------------------");
//        while(true){
//            System.out.println(bThread.getState());
//            System.out.println(bThread.getName());
//        }


    }


}
