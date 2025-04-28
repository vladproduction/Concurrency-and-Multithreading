package com.vladproduction.ch40_ThreadPoolExecutor_Usage.monitoring_and_debugging;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class MonitoredThreadPoolExecutor extends ThreadPoolExecutor {
    private final AtomicLong tasksCompleted = new AtomicLong();

    public MonitoredThreadPoolExecutor(int corePoolSize, int maxPoolSize,
                                       long keepAliveTime, TimeUnit unit,
                                       BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maxPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        tasksCompleted.incrementAndGet();

        // Log any exceptions that occurred
        if (t != null) {
            System.err.println("Task failed with exception: " + t.getMessage());
        }
    }

    public long getCompletedTaskCount() {
        return tasksCompleted.get();
    }

    public void printStats() {
        System.out.println("Pool size: " + getPoolSize());
        System.out.println("Active threads: " + getActiveCount());
        System.out.println("Tasks completed: " + getCompletedTaskCount());
        System.out.println("Tasks in queue: " + getQueue().size());
    }
}
