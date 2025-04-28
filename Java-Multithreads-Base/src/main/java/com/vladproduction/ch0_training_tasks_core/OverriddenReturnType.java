package com.vladproduction.ch0_training_tasks_core;

/**
 * It must be the same type or a subtype of the return type of the overridden method.
 * */
public class OverriddenReturnType {
    public static void main(String[] args) {
        //an overridden return type should have the same type as the parent:
        ClassB b = new ClassB();
        b.methodA();
    }

    static class ClassA{
        public void methodA(){
            System.out.println("methodA");
        }
    }

    static class ClassB extends ClassA{
//        @Override
//        public String methodA(){
//            return "methodB";
//        }
    }


}
