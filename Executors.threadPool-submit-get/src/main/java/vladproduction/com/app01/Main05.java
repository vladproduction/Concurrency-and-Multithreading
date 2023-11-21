package vladproduction.com.app01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main05 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        MyCallable callable = new MyCallable(1, 2, "+");

        //here loop for submit tasks
        List<Future<Integer>> futureList = new ArrayList<>();
        for (int i = 0; i < 50; i++){
            Future<Integer> future = executorService.submit(callable);
            futureList.add(future);
        }
        //loop to get a result
        int sum = 0;
        for(int i = 0; i < futureList.size(); i++){
            Future<Integer> integerFuture = futureList.get(i);
            Integer result = integerFuture.get();
            sum += result;
        }
        System.out.println("sum = " + sum);


        System.out.println("Main-finish");
        long finish = System.currentTimeMillis();
        long resTime = finish - start;
        System.out.println("resTime = " + resTime);

        executorService.shutdown();




    }
}
