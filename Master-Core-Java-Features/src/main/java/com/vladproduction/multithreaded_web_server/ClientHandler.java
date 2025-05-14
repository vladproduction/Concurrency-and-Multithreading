package com.vladproduction.multithreaded_web_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try(OutputStream outputStream = clientSocket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){

            String line;
            while (!(line = reader.readLine()).isEmpty()){
                System.out.println(line);
            }

            String httpResponse = "HTTP/1.1 200 OK\nContent-Type: text/html\nContent-Length: 11\n\nHello World!";
            outputStream.write(httpResponse.getBytes());

        }catch (IOException e){
            System.out.println("Client Error: " + e.getMessage());
        }finally {
            try{
                clientSocket.close();
            }catch (IOException e){
                System.out.println("Failed to close client socket: " + e.getMessage());
            }
        }

    }
}
