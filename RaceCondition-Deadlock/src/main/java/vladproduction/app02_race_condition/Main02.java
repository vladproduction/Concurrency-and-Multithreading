package vladproduction.app02_race_condition;

public class Main02 {
    public static void main(String[] args) throws InterruptedException {
        Main02 main02 = new Main02();
        main02.runAction4();
    }

    private void runAction4() throws InterruptedException {
        //example show condition of threads, start() method  and join() method for threads:
        //1)make methods increment() and decrement() in MyData synchronized;
        //2)or we have to add synchronized block inside increment and decrement methods:
        //so value incrementing and decrementing will be synchronized;
        System.out.println("Resolved problem of 'Race Condition' here: ");
        MyData myData = new MyData();
        final int n = 100_000_000;
        System.out.println("before: "+myData);
        IncrementThread incrementThread = new IncrementThread(myData, n);
        DecrementThread decrementThread = new DecrementThread(myData, n);
        incrementThread.start();
        decrementThread.start();

        try {
            incrementThread.join();
            decrementThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("after: "+myData);
    }

    private void runAction3() {
        //example show condition as threads, start() method for threads:
        //probably will catch 'Race Condition' here
        System.out.println("'Race Condition' here: ");
        MyData myData = new MyData();
        final int n = 1_000_000;
        System.out.println("before: "+myData);
        new IncrementThread(myData, n).start();
        new DecrementThread(myData, n).start();

    }

    private void runAction2() {
        //example show condition as threads, but only run() method:
        System.out.println("only run() method:");
        final int n = 1_000_000;
        MyData myData = new MyData();
        IncrementThread incrementThread = new IncrementThread(myData, n);
        DecrementThread decrementThread = new DecrementThread(myData ,n);
        incrementThread.run();
        decrementThread.run();

    }

    private void runAction() {
        //example show condition one by one ():
        System.out.println("one by one ():");
        MyData myData = new MyData();
        System.out.println(myData);

        int n = 1_000_000;
        for (int i = 0; i < n; i++) {
             myData.increment();
        }
        System.out.println("incremented :"+myData);

        for (int i = 0; i < n; i++) {
            myData.decrement();
        }
        System.out.println("decremented :"+myData);

    }
}
