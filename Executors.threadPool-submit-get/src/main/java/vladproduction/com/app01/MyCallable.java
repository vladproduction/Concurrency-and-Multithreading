package vladproduction.com.app01;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<Integer> {

    private final int a;
    private final int b;
    private String action;

    public MyCallable(int a, int b, String action) {
        this.a = a;
        this.b = b;
        this.action = action;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("MyCallable-start!" + Thread.currentThread().getName());
        Thread.sleep(10000);
        System.out.println("MyCallable-finish!" + Thread.currentThread().getName());
        return a+b;
    }
}
