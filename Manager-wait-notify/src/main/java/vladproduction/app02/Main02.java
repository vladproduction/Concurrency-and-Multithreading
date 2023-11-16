package vladproduction.app02;

import static java.lang.System.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main02 {
    public static void main(String[] args) {
        Main02 main02 = new Main02();
        main02.runAction();
    }

    private void runAction() {
        Computer2 computer2 = new Computer2("Comp_#01");

        Alfred alfred = new Alfred(computer2);
        alfred.start();

        Bob bob = new Bob(computer2);
        bob.start();

        //simulation for updating profile task:
        try {
            SECONDS.sleep(15);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //-->add some logic for profile task:
            /*for synchronized block where this flag is using , we have to have <while> loop(!!!), but not <if>
            * because holder monitor after notify will not stop its waiting thread...
            * the right case - we should check again if we have profile task now or not - so use <while>*/
        Runnable profileTaskStatus = new Runnable() {
            @Override
            public void run() {
                out.println("profile task status add");
                synchronized (computer2){  //synchronization by current monitor
                    computer2.setProfileTask(true); //set the flag(logic for profile task status add)
                    out.println("profile task status is: "+computer2.isProfileTask());
                    computer2.notify(); //and notify the other thread which are waiting for the monitor
                    // about it`s free now
                }
            }
        };
        Thread profileTaskThread = new Thread(profileTaskStatus);
        profileTaskThread.start();
    }
}
