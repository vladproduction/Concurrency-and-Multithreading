package vladproduction.com.app01;

import java.util.concurrent.*;

public class Main04 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        MyCallable callable = new MyCallable(1, 2, "+");
        MyCallable callable2 = new MyCallable(3, 4, "+");
        MyCallable callable3 = new MyCallable(5, 5, "+");
        MyCallable callable4 = new MyCallable(3, 4, "+");
        MyCallable callable5 = new MyCallable(5, 5, "+");

        Future<Integer> future1 = executorService.submit(callable);
        Future<Integer> future2 = executorService.submit(callable2);
        Future<Integer> future3 = executorService.submit(callable3);
        Future<Integer> future4 = executorService.submit(callable4);
        Future<Integer> future5 = executorService.submit(callable5);

        Integer result = future1.get();
        Integer result2 = future2.get();
        Integer result3 = future3.get();
        Integer result4 = future4.get();
        Integer result5 = future5.get();

        System.out.println("result = " + result);
        System.out.println("result2 = " + result2);
        System.out.println("result3 = " + result3);
        System.out.println("result4 = " + result4);
        System.out.println("result5 = " + result5);


        System.out.println("Main-finish");
        long finish = System.currentTimeMillis();
        long resTime = finish - start;
        System.out.println("resTime = " + resTime);

        executorService.shutdown();




    }
}
