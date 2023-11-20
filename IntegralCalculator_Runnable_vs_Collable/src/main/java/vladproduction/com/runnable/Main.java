package vladproduction.com.runnable;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.DoubleUnaryOperator;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run() {
        double a = 0;
        double b = Math.PI;
        int n = 1_000_000_000;
        DoubleUnaryOperator f = Math::sin;

        int nThreads = 100;
        double delta = (b - a) / nThreads;

        totalSum = 0;
        finished = 0;

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < nThreads; i++) {
            IntegralCalculatorThread integralCalculatorThread = new IntegralCalculatorThread(a + i * delta, a + (i + 1) * delta, n / nThreads, f, this);
            new Thread(integralCalculatorThread).start();
        }

        lock = new ReentrantLock();
        condition = lock.newCondition();
        lock.lock();
        try {
            while (finished < nThreads) {
                condition.await();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        long finishTime = System.currentTimeMillis();
        System.out.println("result = " + totalSum);
        System.out.println(finishTime - startTime);
    }

    public void sendResult(double v) {
        try {
            lock.lock();
            totalSum += v;
            finished++;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    private double totalSum;
    private int finished;

    Lock lock;
    Condition condition;
}
