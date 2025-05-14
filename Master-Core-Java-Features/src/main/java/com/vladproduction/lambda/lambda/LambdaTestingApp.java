package com.vladproduction.lambda.lambda;

public class LambdaTestingApp {
    public static void main(String[] args) {

        //Anonymous class:
        Animals animals = new Animals() {
            @Override
            public void show(String animal, int speed) {
                System.out.println("The " + animal + " is running at " + speed + " km/h");
            }
        };
        animals.show("Cheetah", 90);

        //Lambda expression:
        Animals animalsLambda = (a, s) -> {
            System.out.println("The " + a + " is running at " + s + " km/h");
        };
        animalsLambda.show("Jaguar", 120);

        //by lambda if we have a helper method to display:
        Animals animalsLambdaMethod = (a, s) -> LambdaTestingApp.display(a, s);
        animalsLambdaMethod.show("Lion", 90);

        //by method reference if we have a helper method to display:
        Animals animalsReference = LambdaTestingApp::display;
        animalsReference.show("Tiger", 100);
    }

    public static void display(String animal, int speed){
        System.out.println("The " + animal + " is running at " + speed + " km/h");
    }

}
