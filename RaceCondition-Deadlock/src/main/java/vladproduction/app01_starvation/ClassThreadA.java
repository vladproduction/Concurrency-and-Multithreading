package vladproduction.app01_starvation;

import java.util.Date;

import static java.lang.System.*;

public class ClassThreadA extends Thread{

//    @Override
//    public void run(){
//        out.println("ClassThreadA "+new Date());
//    }

    @Override
    public void run(){
        while (true){
            out.println("ClassThreadA "+new Date());
        }

    }

}
