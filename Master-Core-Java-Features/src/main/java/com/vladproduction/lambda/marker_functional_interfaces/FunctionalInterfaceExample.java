package com.vladproduction.lambda.marker_functional_interfaces;

public class FunctionalInterfaceExample {

    //Runnable is one of the interfaces which are considered as functional

    Runnable runnable = new Runnable() {

        //here is only one abstract method, but allows to have static and default methods also
        @Override
        public void run() {
            //some code
        }
    };

}
