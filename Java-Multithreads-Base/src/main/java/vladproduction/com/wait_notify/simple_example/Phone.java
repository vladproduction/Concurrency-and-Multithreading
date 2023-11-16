package vladproduction.com.wait_notify.simple_example;

public class Phone {
    private boolean bossInOffice = false;


    public synchronized  void makeCall(long duration){
        System.out.println("makeCall() - start "+ Thread.currentThread());
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
        System.out.println("makeCall() - end "+ Thread.currentThread());
    }

    public boolean isBossInOffice() {
        return bossInOffice;
    }

    public void setBossInOffice(boolean bossInOffice) {
        this.bossInOffice = bossInOffice;
    }
}
