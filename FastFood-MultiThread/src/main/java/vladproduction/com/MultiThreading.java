package vladproduction.com;

import java.util.Scanner;

public class MultiThreading {
    
    public static void main(String[] args) throws Exception {
        Scanner inputReader = new Scanner(System.in);
        String userinput;

        Chef chef = new Chef();
        Entry comingcustomers = new Entry();
        Attender attender1 = new Attender(0);
        Attender attender2 = new Attender(1);
        
        chef.start();
        comingcustomers.start();
        attender1.start();
        attender2.start();

        //Stop with any key, except just Enter
        while(true){
            userinput = inputReader.next();
            chef.end();
            comingcustomers.end();
            attender1.end();
            attender2.end();
            System.exit(0);
        }
    }
}

