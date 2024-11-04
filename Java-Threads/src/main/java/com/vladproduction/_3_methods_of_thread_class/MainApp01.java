package com.vladproduction._3_methods_of_thread_class;

public class MainApp01 {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Main thread is printing: " + i);
            if(i == 5){
                System.out.println("Main thread paused on 2 sec");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
/*Main thread is printing: 1
Main thread is printing: 2
Main thread is printing: 3
Main thread is printing: 4
Main thread is printing: 5
Main thread paused on 2 sec
Main thread is printing: 6
Main thread is printing: 7
Main thread is printing: 8
Main thread is printing: 9
Main thread is printing: 10*/
