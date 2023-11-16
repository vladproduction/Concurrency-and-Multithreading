package vladproduction.com.wait_notify.simple_example;

public class ClassA extends Thread {
    private final Phone phone;

    public ClassA(Phone phone) {
        this.phone = phone;
        setName("ClassA");
    }

    public void run(){
        System.out.println("ClassA started");
        synchronized (phone){
            System.out.println("ClassA got phone (synchronized)");
            while(!phone.isBossInOffice()) {
                try {
                    System.out.println("ClassA decided to wait for boss (wait)");
                    phone.wait();
                    System.out.println("ClassA after wait");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        phone.makeCall(15000);
        System.out.println("ClassA finished");
    }

}
