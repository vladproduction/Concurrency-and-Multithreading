package com.vladproduction.ch0_training.nonstaticinnerclass;

public class Main {
    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        OuterClass.InnerClass innerObject = outer.new InnerClass();
        innerObject.display();
    }
}
