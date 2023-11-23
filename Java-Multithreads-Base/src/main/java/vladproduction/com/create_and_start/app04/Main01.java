package vladproduction.com.create_and_start.app04;

import static java.lang.System.*;
import static java.lang.Thread.*;

public class Main01 {
    public static void main(String[] args) {
        out.println(currentThread().getName());
        Thread thread = new MyThread();
        thread.start();
    }

    private static class MyThread extends Thread{
        @Override
        public void run(){
            out.println(currentThread().getName());
        }
    }
}
