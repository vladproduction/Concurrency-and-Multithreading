package vladproduction.com;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;

public class IntegralCalculator {
    private final double a;
    private final int n;
    private final DoubleUnaryOperator f;
    private final double h;

    public IntegralCalculator(double a, double b, int n, DoubleUnaryOperator f) {
        this.a = a;
        this.n = n;
        this.f = f;
        h = (b - a) / n;
    }

    public double calculate() {

        return IntStream.range(0, n).mapToDouble(i -> a + i * h).map(f).sum() * h;
    }
}
