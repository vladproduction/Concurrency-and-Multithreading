package vladproduction.com;

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

        int nThreads = 20;
        double delta = (b - a) / nThreads;

        totalSum = 0;
        finished = 0;

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < nThreads; i++) {
            IntegralCalculatorThread integralCalculatorThread = new IntegralCalculatorThread(
                    a + i * delta,
                    a + (i + 1) * delta,
                    n / nThreads,
                    f,
                    this);
            new Thread(integralCalculatorThread).start();
        }

        try {
            synchronized (this) {
                while (finished < nThreads) {
                    wait();
                }
            }
            long finishTime = System.currentTimeMillis();
            System.out.println("totalSum = " + totalSum);
            System.out.println(finishTime - startTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void run1() {
        // sin(x)
        double a = 0;
        double b = Math.PI;
        int n = 1_000_000_000;

        IntegralCalculator calculator = new IntegralCalculator(a, b, n, Math::sin);
        long startTime = System.currentTimeMillis();
        double result = calculator.calculate();
        long finishTime = System.currentTimeMillis();

        System.out.println("result = " + result);
        System.out.println(finishTime - startTime);
    }

    public synchronized void sendResult(double v) {
        totalSum += v;
        finished++;
        notify();
    }

    private double totalSum;
    private int finished;
}
