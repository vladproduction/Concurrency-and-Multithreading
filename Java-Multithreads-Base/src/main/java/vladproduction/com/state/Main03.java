package vladproduction.com.state;

import static java.lang.String.format;
import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class Main03 {

    private static final String MESSAGE_TEMPLATE_THREAD_STATE = "%s : %s\n";
    private static final int SLEEPING_IN_MAIN_THREAD = 1000; //MILLISECONDS
    private static final int SLEEPING_TO_JOIN_IN_THREAD_ON_MAIN_THREAD = 6000;

    public static void main(String[] args) throws InterruptedException{
        Thread mainThread = currentThread();
        Thread thread = new Thread(()->{
            try{
                mainThread.join(SLEEPING_TO_JOIN_IN_THREAD_ON_MAIN_THREAD);
                showThreadState(currentThread());
            }catch (InterruptedException interruptedException){

            }
        });
        thread.start();
        sleep(SLEEPING_IN_MAIN_THREAD);
        showThreadState(thread);
    }

    private static void showThreadState(Thread thread){
        out.println(format(MESSAGE_TEMPLATE_THREAD_STATE, thread.getName(), thread.getState()));

    }
}
