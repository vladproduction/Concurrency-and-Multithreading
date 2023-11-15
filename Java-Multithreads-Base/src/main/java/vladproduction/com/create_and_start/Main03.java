package vladproduction.com.create_and_start;

import static java.lang.System.*;
import static java.lang.Thread.*;

public class Main03 {
    public static void main(String[] args) {
        Runnable task = ()->{out.println(currentThread().getName());};
        Thread thread = new Thread(task);
        thread.setName("thread-task");
        thread.start();
    }
}
