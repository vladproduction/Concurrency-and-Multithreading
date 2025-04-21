package com.vladproduction.ch01_core_concepts.thread_communication;

import java.util.ArrayList;
import java.util.List;

// Example 3: Thread Signaling with wait/notifyAll for Task Coordination
public class TaskCoordinator {

    private enum Status { NOT_STARTED, RUNNING, COMPLETED }

    static class Task implements Runnable {
        private final String name;
        private final long duration;
        private Status status = Status.NOT_STARTED;
        private final List<Task> dependencies = new ArrayList<>();

        public Task(String name, long duration) {
            this.name = name;
            this.duration = duration;
        }

        public void addDependency(Task task) {
            dependencies.add(task);
        }

        @Override
        public void run() {
            // First wait for all dependencies to complete
            for (Task dependency : dependencies) {
                synchronized (dependency) {
                    while (dependency.status != Status.COMPLETED) {
                        try {
                            System.out.println(name + " waiting for dependency: " +
                                    dependency.name);
                            dependency.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                }
            }

            // Now execute this task
            synchronized (this) {
                this.status = Status.RUNNING;
                System.out.println(name + " started execution");
            }

            try {
                // Simulate work
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            // Mark as completed and notify waiting tasks
            synchronized (this) {
                this.status = Status.COMPLETED;
                System.out.println(name + " completed execution");
                notifyAll(); // Wake up all tasks waiting on this task
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) {
        // Create tasks with dependencies
        Task taskA = new Task("Task-A", 1000);
        Task taskB = new Task("Task-B", 2000);
        Task taskC = new Task("Task-C", 1500);
        Task taskD = new Task("Task-D", 500);
        Task taskE = new Task("Task-E", 1000);

        // Set up dependencies (DAG - Directed Acyclic Graph)
        // D depends on B and C
        taskD.addDependency(taskB);
        taskD.addDependency(taskC);

        // E depends on A and D
        taskE.addDependency(taskA);
        taskE.addDependency(taskD);

        // Create threads for each task
        Thread threadA = new Thread(taskA);
        Thread threadB = new Thread(taskB);
        Thread threadC = new Thread(taskC);
        Thread threadD = new Thread(taskD);
        Thread threadE = new Thread(taskE);

        // Start all threads - they'll coordinate themselves
        threadE.start(); // Starting in reverse order to demonstrate coordination
        threadD.start();
        threadC.start();
        threadB.start();
        threadA.start();

        // Wait for the final task to complete
        try {
            threadE.join();
            System.out.println("All tasks completed successfully");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
