package com.vladproduction.ch0_training_tasks_core;

import java.util.function.Function;

public class LambdaTesting01 {
    public static void main(String[] args) {
        Function<String, String> f1 = s -> s + "-f1-";
        Function<String, String> f2 = s -> s + "-f2-";
        Function<String, String> f3 = s -> s + "-f3-";

        System.out.println(f1.andThen(f3).compose(f2).apply("Compose"));
        System.out.println(f1.andThen(f2).andThen(f3).apply("AndThen"));



    }
}
