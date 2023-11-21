package vladproduction.com.app01;

import java.util.concurrent.*;

public class Main02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        MyCallable callable = new MyCallable(1, 2, "+");
        Future<Integer> future = executorService.submit(callable);
//        Integer result = future.get();
//        System.out.println("result = " + result);
//        Integer i = future.get(5, TimeUnit.SECONDS); //TimeoutException
//        System.out.println("i = " + i);
//        while (!future.isDone()){
//            System.out.println("threadPool is still working");
//        }
        Thread.sleep(2000);
        //future.cancel(true);// stop execute anyway
        //future.cancel(false);// stop execute not available
        System.out.println("Main-finish");
        Thread.sleep(9000);
        boolean isCancelled = future.isCancelled();
        System.out.println("isCancelled = " + isCancelled);
        executorService.shutdown();




    }
}
