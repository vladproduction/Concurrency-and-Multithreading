package com.vladproduction.ch40_ThreadPoolExecutor_Usage.real_world_cases;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledMaintenanceTasks {
    public static void main(String[] args) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // Run database cleanup every night at midnight
        Runnable cleanup = () -> cleanupDatabase();
        long initialDelay = calculateInitialDelay(); // Time until midnight
        scheduler.scheduleAtFixedRate(cleanup, initialDelay, 24, TimeUnit.HOURS);

        // Check system health every 5 minutes
        Runnable healthCheck = () -> checkSystemHealth();
        scheduler.scheduleAtFixedRate(healthCheck, 0, 5, TimeUnit.MINUTES);


    }

    private static void checkSystemHealth() {
        // Code to check system health
    }

    private static long calculateInitialDelay() {
        return 0;
    }

    private static void cleanupDatabase() {
        // Code to clean up the database
    }
}
