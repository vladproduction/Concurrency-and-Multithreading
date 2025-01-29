package com.vladproduction.read_write_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {

        //readers (create 10 readers threads)
        for (int i = 0; i < 10; i++) {
            new CalendarUser("Reader-" + i).start();
        }

        //writers (create only 2 writers)
        for (int i = 0; i < 2; i++) {
            new CalendarUser("Writer-" + i).start();
        }

    }
}

class CalendarUser extends Thread {

    private static final String[] WEEK_DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private static int today = 0;
    private static final ReentrantReadWriteLock marker = new ReentrantReadWriteLock();
    private static final Lock readMarker = marker.readLock();
    private static final Lock writeMarker = marker.writeLock();

    public CalendarUser(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (today < WEEK_DAYS.length - 1) {
            if(this.getName().contains("Writer")){ //update the shared calendar
                writeMarker.lock();
                try{
                    today = (today + 1) % 7;
                    System.out.println(this.getName() + " updated date to " + WEEK_DAYS[today]);
                }catch(Exception e){
                    e.printStackTrace();
                }
                {
                    writeMarker.unlock();
                }
            } else { // Reader - check to see what today is
                readMarker.lock();
                try{
                    System.out.println(this.getName() + " sees that today is: " + WEEK_DAYS[today] + "; total readers: " + marker.getReadLockCount());
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    readMarker.unlock();
                }
            }
        }
    }

}

