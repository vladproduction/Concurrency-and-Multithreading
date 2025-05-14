package com.vladproduction.real_time_chat_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {

        runnerApp();

    }

    private static void runnerApp() {
        try(Socket socket = new Socket(SERVER_ADDRESS, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to chat server.");

            new Thread(()->{
                String message;
                try{
                    while ((message = in.readLine()) != null) {
                        System.out.println("Received: " + message);
                    }
                }catch (IOException e){
                    System.out.println("Connection error: " + e.getMessage());
                }
            }).start();

            String userInput;
            while ((userInput = console.readLine()) != null){
                out.println(userInput);
            }

        }catch (IOException e){
            System.out.println("Client error: " + e.getMessage());
        }
    }

}

















