package vladproduction.com.state;

import static java.lang.String.format;
import static java.lang.System.*;
import static java.lang.Thread.*;

public class Main01 {

    private static final String MESSAGE_TEMPLATE_THREAD_STATE = "%s : %s\n";

    public static void main(String[] args) {
        Thread thread = new Thread(()->showThreadState(currentThread()));
        showThreadState(thread);
        thread.start();
    }

    private static void showThreadState(Thread thread){
        out.println(format(MESSAGE_TEMPLATE_THREAD_STATE, thread.getName(), thread.getState()));

    }
}
