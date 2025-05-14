package com.vladproduction.lambda.predicate;

import java.util.function.Predicate;

public class PredicateInterfaceExample {
    public static void main(String[] args) {

        Predicate<String> p0 = s -> (s.equals("Java"));
        boolean result0 = p0.test("Java");
        System.out.println("result0 = " + result0); //true

        Predicate<Integer> p1 = i -> (i < 10);
        boolean result1 = p1.test(5);
        System.out.println("result1 = " + result1); //true

        //and
        Predicate<Integer> p2 = m -> m > 5;
        boolean result2 = p1.and(p2).test(7);
        System.out.println("result2 = " + result2); //true

        //or
        Predicate<Integer> p3 = m -> m > 5;
        boolean result3 = p1.or(p3).test(7);
        System.out.println("result3 = " + result3); //true

    }
}
