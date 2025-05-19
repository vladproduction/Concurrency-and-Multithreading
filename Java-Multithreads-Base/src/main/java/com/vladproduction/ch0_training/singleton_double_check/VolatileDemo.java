package com.vladproduction.ch0_training.singleton_double_check;

public class VolatileDemo {
    public static void main(String[] args) {

    }

    static class Person {
        private String name;
        private Long id;
        private volatile Integer age;

        public Person(String name, Long id, Integer age) {
            this.name = name;
            this.id = id;
            this.age = age;
        }
    }
}
