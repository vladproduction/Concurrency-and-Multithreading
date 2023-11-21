package vladproduction.com.app01;

import java.util.concurrent.*;

public class Main03 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        MyCallable callable = new MyCallable(1, 2, "+");
        Future<Integer> future1 = executorService.submit(callable);
        Integer result = future1.get();

        System.out.println("result = " + result);
        System.out.println("add new tasks");
        MyCallable callable2 = new MyCallable(3, 4, "+");
        Future<Integer> future2 = executorService.submit(callable2);
        Integer result2 = future2.get();
        System.out.println("result2 = " + result2);
        MyCallable callable3 = new MyCallable(5, 5, "+");
        Future<Integer> future3 = executorService.submit(callable3);
        Integer result3 = future3.get();
        System.out.println("result3 = " + result3);


        System.out.println("Main-finish");
        long finish = System.currentTimeMillis();
        long resTime = finish - start;
        System.out.println("resTime = " + resTime);

        executorService.shutdown();




    }
}
