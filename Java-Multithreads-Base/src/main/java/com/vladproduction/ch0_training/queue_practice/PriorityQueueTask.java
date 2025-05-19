package com.vladproduction.ch0_training.queue_practice;

import java.util.PriorityQueue;

public class PriorityQueueTask {

    public static void main(String[] args) {

        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("2");
        pq.add("4");
        System.out.print(pq.peek() + " "); //2
        pq.offer("1"); //124
        pq.add("3");//1234
        pq.remove("1");//234
        System.out.print(pq.poll() + " ");//2
        if(pq.remove("2")) System.out.print(pq.poll() + " ");//3
        System.out.println(pq.poll() + " " + pq.peek());//4

    }


}
