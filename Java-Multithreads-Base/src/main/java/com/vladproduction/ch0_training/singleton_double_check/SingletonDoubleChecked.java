package com.vladproduction.ch0_training.singleton_double_check;

public class SingletonDoubleChecked {

    private static volatile SingletonDoubleChecked instance;

    private SingletonDoubleChecked() {

    }

    public static SingletonDoubleChecked getInstance() {

        if (instance == null) {
            synchronized (SingletonDoubleChecked.class) {
                if (instance == null) {
                    instance = new SingletonDoubleChecked();
                }
            }
        }
        return instance;
    }

}
