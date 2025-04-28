package com.vladproduction.ch40_ThreadPoolExecutor_Usage.real_world_cases;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelDataProcessing {
    public static void main(String[] args) {

        List<String> bigData = new ArrayList<>();

        // Process a large dataset in parallel
        List<DataChunk> dataChunks = DataChunk.splitDataIntoChunks(bigData);
        ExecutorService processor = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());

        List<Future<ProcessingResult>> results = dataChunks.stream()
                .map(chunk -> processor.submit(() -> processChunk(chunk)))
                .toList();

        // Collect all results
        List<ProcessingResult> processedResults = new ArrayList<>();
        for (Future<ProcessingResult> result : results) {
            try {
                processedResults.add(result.get());
            } catch (Exception e) {
                // Handle exceptions
            }
        }

    }

    private static ProcessingResult processChunk(DataChunk chunk) {
        return null;
    }

    private static class DataChunk{

        private static List<DataChunk> splitDataIntoChunks(List<String> data){
            return new ArrayList<>();
        }

    }

    private static class ProcessingResult{
        // some code here
    }

}
