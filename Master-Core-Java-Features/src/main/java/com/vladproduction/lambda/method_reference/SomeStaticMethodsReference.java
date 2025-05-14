package com.vladproduction.lambda.method_reference;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class SomeStaticMethodsReference {
    public static void main(String[] args) {

        //static method reference

        //String -> int converting
        Function<String, Integer> stringToInt = Integer::parseInt;
        Integer integer123 = stringToInt.apply("123");
        System.out.println(integer123);
        System.out.println(stringToInt.apply("123456789"));

        //int -> String
        Function<Integer, String> intToString = String::valueOf;
        String string123 = intToString.apply(123);
        System.out.println(string123);
        System.out.println(intToString.apply(123456789));

        //method reference via an Object
        String str = "Hello";
//        Supplier<String> resultInUpper = () -> str.toUpperCase(); //lambda
        Supplier<String> resultInUpper = str::toUpperCase;
        System.out.println(resultInUpper.get());

        //calling constructor of the class:
//        Supplier<List<String>> listSupplier = () -> new ArrayList<>(); //lambda
        Supplier<List<String>> listSupplier = ArrayList::new;
        List<String> myList = listSupplier.get();


    }
}
