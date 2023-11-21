package vladproduction.com.app01;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        MyCallable callable = new MyCallable(1, 2, "+");
        Future<Integer> future = executorService.submit(callable);
        Integer result = future.get(); //state as blocking method
        Integer resultWithTimOut = future.get(1, TimeUnit.MINUTES);//if more sec = Exception throw directly
        boolean doneWork = future.isDone(); //done or not
        while (!future.isDone()){

        }
        future.get();


    }
}
