package com.vladproduction._6_semaphores;

class PrintJob extends Thread {
    private SharedPrinter printer;
    private String document;

    PrintJob(SharedPrinter printer, String document) {
        this.printer = printer;
        this.document = document;
    }

    @Override
    public void run() {
        printer.printDocument(document);
    }
}
