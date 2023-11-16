package vladproduction.com.wait_notify.blocking_queue;

import java.util.ArrayList;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        MainApp mainApp = new MainApp();
        mainApp.runAction();
    }

    private void runAction() {
        BlockingQueue blockingQueue = new BlockingQueue();

        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){ //infinity loop for working with tasks which are adding
                    Runnable task = blockingQueue.get(); //here we get some task from queue;
                    task.run();
                }
            }
        });
        worker.start(); //here start worker thread
        for (int i = 0; i < 5; i++) {  //5 tasks in
             blockingQueue.put(getTask());
        }
    }

    public static Runnable getTask(){
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("TASK STARTED: "+ this);
                try {
                    Thread.sleep(1000); // 1 sec between tasks to be done
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("TASK FINISHED: "+ this);
            }
        };
    }

    //special class for blocking queue of tasks
    static class BlockingQueue {
        //container of tasks which are waiting to be done
        List<Runnable> tasks = new ArrayList<>();
        //realize method get which is synchronized
        public synchronized Runnable get(){
            //while queue empty-->have to wait
            while (tasks.isEmpty()){
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //as soon as we get task, return it and delete from container tasks
            Runnable task = tasks.get(0);
            tasks.remove(task);
            return task;
        }

        //method for add tasks
        public synchronized void put(Runnable task) {
            tasks.add(task);
            notify();
        }

    }
}
