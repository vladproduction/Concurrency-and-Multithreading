package com.vladproduction.ch0_training.switch_stmt;

public class SwitchStatementTesting {
    public static void main(String[] args) {


        switch (9) {
            case 9:
                System.out.println("9");
        }

        enum Vegetable {
            tomato, potato, cucumber
        }

        Vegetable p = Vegetable.potato;
        switch (p) {
            case tomato:
            case potato:
            case cucumber:
                System.out.println("vegetable");
        }

    }


}
