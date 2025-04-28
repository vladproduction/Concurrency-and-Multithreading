package com.vladproduction.ch0_training_tasks_core;

import java.io.IOException;

public class FileReaderTest {
    public static void main(String[] args) throws IOException {
        readLine("a.txt");
        System.out.print("Done");
    }

    public static String readLine(String fileName) throws IOException {
//        try (FileReader f = new FileReader(fileName)|
//             BufferedReader br = new BufferedReader(f)) {
//            System.out.print("Reading line ");
//            return br.readLine();
//        }
        return "null";
    }
}