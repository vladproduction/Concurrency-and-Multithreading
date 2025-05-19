package com.vladproduction.ch0_training.flow_controls;

class FlowControlExample02 {
    public static void main(String[] args) {

        foreach:
        for (int j = 0; j < 5; j++) {
            for (int k = 0; k < 3; k++) {
                System.out.print(" " + j);
                if(j == 3 && k == 1) break foreach;
                if(j == 0 || j == 2) break;

            }


        }

    }
}