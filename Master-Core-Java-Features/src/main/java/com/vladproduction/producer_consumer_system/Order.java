package com.vladproduction.producer_consumer_system;

public record Order(int orderId, String productName, int quantity) {

//    private int orderId;
//    private String productName;
//    private int quantity;
//
//    public Order(int orderId, String productName, int quantity) {
//        this.orderId = orderId;
//        this.productName = productName;
//        this.quantity = quantity;
//    }
//
//    @Override
//    public String toString() {
//        return "Order{" +
//                "orderId=" + orderId +
//                ", productName='" + productName + '\'' +
//                ", quantity=" + quantity +
//                '}';
//    }
}
