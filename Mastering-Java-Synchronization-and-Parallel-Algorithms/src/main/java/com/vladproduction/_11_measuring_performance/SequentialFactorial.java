package com.vladproduction._11_measuring_performance;

class SequentialFactorial {
    public static long computeFactorial(long n) {
        if (n <= 1) {
            return 1;
        }
        return n * computeFactorial(n - 1);
    }
}
