package com.vladproduction.ch40_ThreadPoolExecutor_Usage.real_world_cases;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServerRequestHandler {

    private ExecutorService requestProcessor;

    public static void main(String[] args) {

        // Simplified web server handling incoming requests with a thread pool
        ExecutorService requestProcessor = Executors.newFixedThreadPool(100);

    }

    public void handleRequest(Socket clientSocket) {
        requestProcessor.submit(() -> {
            try (InputStream in = clientSocket.getInputStream();
                 OutputStream out = clientSocket.getOutputStream()) {
                // Process HTTP request and generate response
                processRequest(in, out);
            } catch (IOException e) {
                // Error handling
            }
        });
    }
    private static void processRequest(InputStream in, OutputStream out) throws IOException {
        //some code here
    }



}
