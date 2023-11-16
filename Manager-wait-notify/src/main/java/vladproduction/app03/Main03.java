package vladproduction.app03;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main03 {
    public static void main(String[] args) {
        Main03 main03 = new Main03();
        main03.runAction();
    }

    private void runAction() {
        Computer3 computer3 = new Computer3("Comp_#01");

        Alfred alfred = new Alfred(computer3);
        alfred.start();

        Daniel daniel = new Daniel(computer3);
        daniel.start();

        Bob bob = new Bob(computer3);
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
                out.println("\t\t-->profile_task_status_add");
                synchronized (computer3){  //synchronization by current monitor
                    computer3.setProfileTask(true); //set the flag(logic for profile task status add)
                    out.println("\t\tprofile_task_status_is: "+ computer3.isProfileTask());
                    computer3.notifyAll(); //and notify the other thread which are waiting for the monitor
                    // about it`s free now
                    //notifyAll(); all waiting threads will be able to continue work with monitor, and finish task
                    //but no guaranty of which thread will be from that wait_set first;
                }
            }
        };
        Thread profileTaskThread = new Thread(profileTaskStatus);
        profileTaskThread.start();
    }
}
