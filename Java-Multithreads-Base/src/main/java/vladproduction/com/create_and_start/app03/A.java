package vladproduction.com.create_and_start.app03;

public class A  extends Thread {
    private final Phone phone;

    public A(Phone phone) {
        this.phone = phone;
        setName("A");
    }

    public void run(){
        System.out.println("A started");
        phone.makeCall(5000);
        System.out.println("A finished");
    }

}
