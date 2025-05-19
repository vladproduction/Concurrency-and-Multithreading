package com.vladproduction.ch0_training.file_reader;

import java.io.FileReader;
import java.io.IOException;

public class FileReaderTest02 {
    public static void main(String[] args) {

        try (FileReader f = new FileReader("/a.txt")) {
            System.out.print("Try block ");
        } catch (IOException e) {
            System.out.print("Catch block ");
        }
//        System.out.print(f.read());
    }
}