package com.vladproduction.multithreaded_web_server;

public class MainApp {
    public static void main(String[] args) {

        MultithreadedWebServer server = new MultithreadedWebServer(8080);
        Thread serverThread = new Thread(server::startServer);
        serverThread.start();
        try{
            serverThread.join();
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }

    }
}
