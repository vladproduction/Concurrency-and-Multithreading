package vladproduction.com.wait_notify.simple_example;

public class ClassB implements Runnable {
    private final Phone phone;

    public ClassB(Phone phone) {
        this.phone = phone;
    }

    @Override
    public void run() {
        System.out.println("ClassB started");
        synchronized (phone){
            System.out.println("ClassB got phone (synchronized)");
            phone.makeCall(10000);
        }

        System.out.println("ClassB finished");
    }
}
