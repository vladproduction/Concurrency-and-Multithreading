package vladproduction.com;

import java.util.Random;

class Chef extends Thread {
    Random randomgenerator = new Random();
    private boolean threadalive = true;

    public Chef() {
        ;
    }

    public void run() {
        int timetocook = 0, wichfood = 0;
        while(this.threadalive){
            timetocook = 2000 + 1000*randomgenerator.nextInt(3);

            try {
                this.sleep(timetocook);

                wichfood =  randomgenerator.nextInt(3);
                System.out.print("Chef on " + (timetocook/1000) + " seconds Cook a ");
                switch(wichfood){
                    case 0:
                        FoodStock.cooksellburger("cook");
                        System.out.println("burger");
                        break;
                    case 1:
                        FoodStock.cooksellsandwich("cook");
                        System.out.println("sandwich");
                        break;
                    case 2:
                        FoodStock.cooksellsalad("cook");
                        System.out.println("salad");
                        break;
                }
            } catch(InterruptedException e){
                System.out.println("Chef Interrupted");
            }
        }
    }

    public void end(){
        this.threadalive = false;
    }
}
