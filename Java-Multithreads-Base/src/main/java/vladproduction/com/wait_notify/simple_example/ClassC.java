package vladproduction.com.wait_notify.simple_example;

public class ClassC extends Thread {
    private final Phone phone;

    public ClassC(Phone phone) {
        this.phone = phone;
        setName("ClassC");
    }

    public void run(){
        System.out.println("ClassC started");
        synchronized (phone){
            System.out.println("ClassC got phone (synchronized)");
            while(!phone.isBossInOffice()) {
                try {
                    System.out.println("ClassC decided to wait for boss (wait)");
                    phone.wait();
                    System.out.println("ClassC after wait");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        phone.makeCall(15000);
        System.out.println("ClassC finished");
    }

}
