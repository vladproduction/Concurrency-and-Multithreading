package vladproduction.com.collable;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;

public class IntegralCalculator {
    private double a;
    private double b;
    private int n;
    private DoubleUnaryOperator f;
    private double h;

    public IntegralCalculator(double a, double b, int n, DoubleUnaryOperator f) {
        this.a = a;
        this.b = b;
        this.n = n;
        this.f = f;
        h = (b - a) / n;
    }

    public double calculate() {
        return IntStream.range(0, n).mapToDouble(i -> a + i * h).map(f).sum() * h;
    }
}
