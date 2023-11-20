package vladproduction.com.runnable;

import java.util.function.DoubleUnaryOperator;

public class IntegralCalculatorThread implements Runnable {

    private IntegralCalculator calculator;

    private Main main;

    public IntegralCalculatorThread(double a, double b, int n, DoubleUnaryOperator f, Main main) {
        this.calculator = new IntegralCalculator(a, b, n, f);
        this.main = main;
    }

    @Override
    public void run() {
        double v = calculator.calculate();
        main.sendResult(v);
    }
}
