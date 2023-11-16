package vladproduction.app01_starvation;

import java.util.Date;

import static java.lang.System.out;

public class ClassThreadB extends Thread{

    //    @Override
//    public void run(){
//        out.println("ClassThreadB "+new Date());
//    }

    @Override
    public void run(){
        for (;;) {
            out.println("\tClassThreadB "+new Date());
        }
    }

}
