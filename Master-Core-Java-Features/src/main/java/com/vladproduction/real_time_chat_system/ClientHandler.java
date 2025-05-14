package com.vladproduction.real_time_chat_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private final Socket clientSocket;
    private PrintWriter out;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            String message;
            while ((message = in.readLine()) != null){
                System.out.println("Received: " + message);
                ChatServer.broadcast(message, this);
            }
        }catch (IOException e){
            System.out.println("Client error: " + e.getMessage());
        }finally {
            ChatServer.removeClient(this);
            try{
                clientSocket.close();
            }catch (IOException e){
                System.out.println("Failed to close client socket: " + e.getMessage());
            }
        }
    }

    public void sendMessage(String message){
        if(out != null){
            out.println(message);
        }
    }

}
