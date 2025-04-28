package com.vladproduction.ch31_ConcurrentHashMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class MultiThreadLetterCounter extends LetterCounter{

    public MultiThreadLetterCounter(int subtaskCount) {
        super(subtaskCount);
    }

    @Override
    protected Map<Character, Integer> createAccumulator() {
        return new ConcurrentHashMap<>();
    }

    @Override
    protected void execute(Stream<Subtask> subtasks) {
        List<Thread> threads = run(subtasks);
        threads.forEach(this::waitUntilFinish);

    }

    private List<Thread> run(Stream<Subtask> subtasks) {
        return subtasks.map(this::run).toList();

    }

    private Thread run(Subtask subtask) {
        Thread thread = new Thread(subtask::execute);
        thread.start();
        return thread;
    }

    private void waitUntilFinish(Thread thread) {
        try{
            thread.join();
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

}
