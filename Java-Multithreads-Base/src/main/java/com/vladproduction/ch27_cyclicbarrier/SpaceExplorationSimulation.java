package com.vladproduction.ch27_cyclicbarrier;

import java.util.concurrent.*;

/**
 * This class demonstrates using CyclicBarrier to coordinate multiple threads
 * in a space exploration simulation.
 */
public class SpaceExplorationSimulation {

    // Number of astronauts on the mission
    private static final int ASTRONAUT_COUNT = 4;
    // Number of exploration phases
    private static final int EXPLORATION_PHASES = 3;

    public static void main(String[] args) {
        System.out.println("Space Exploration Mission Beginning!");

        // Create a barrier with a runnable that executes when all threads reach the barrier
        CyclicBarrier missionBarrier = new CyclicBarrier(ASTRONAUT_COUNT, () -> {
            System.out.println("\n=== All astronauts have completed current mission phase! ===\n");
        });

        ExecutorService executorService = Executors.newFixedThreadPool(ASTRONAUT_COUNT);

        // Launch astronauts
        for (int i = 1; i <= ASTRONAUT_COUNT; i++) {
            final String astronautName = "Astronaut-" + i;

            executorService.submit(() -> {
                try {
                    // Each astronaut performs multiple phases of exploration
                    for (int phase = 1; phase <= EXPLORATION_PHASES; phase++) {
                        System.out.println(astronautName + " starting exploration phase " + phase);

                        // Simulate exploration work
                        performExplorationTasks(astronautName, phase);

                        System.out.println(astronautName + " reached rendezvous point for phase " + phase +
                                " - waiting for others. (" +
                                (missionBarrier.getNumberWaiting() + 1) + "/" +
                                ASTRONAUT_COUNT + " arrived)");

                        // Wait at the barrier until all astronauts complete this phase
                        missionBarrier.await();

                        // All astronauts have synchronized here and continue to next phase
                        System.out.println(astronautName + " proceeding to " +
                                (phase < EXPLORATION_PHASES ? "phase " + (phase + 1) : "return home"));
                    }

                    System.out.println(astronautName + " has completed all exploration phases!");

                } catch (InterruptedException e) {
                    System.err.println(astronautName + "'s mission interrupted!");
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    System.err.println("Mission coordination barrier broken! " + astronautName + " aborting.");
                }
            });
        }

        // Shutdown the executor service after all tasks complete
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.MINUTES)) {
                System.err.println("Mission timeout! Some astronauts did not complete their tasks.");
                executorService.shutdownNow();
            } else {
                System.out.println("\nMission complete! All astronauts have returned safely.");
            }
        } catch (InterruptedException e) {
            System.err.println("Main mission control interrupted!");
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Simulates an astronaut performing exploration tasks
     */
    private static void performExplorationTasks(String astronautName, int phase) throws InterruptedException {
        // Different phases have different exploration times and tasks
        int baseDuration = 1000 + phase * 500;
        int randomVariation = ThreadLocalRandom.current().nextInt(1000);
        int explorationTime = baseDuration + randomVariation;

        // Simulate the work
        Thread.sleep(explorationTime);

        // Report findings
        String[] tasks = {
                "collected soil samples",
                "took atmospheric readings",
                "documented alien flora",
                "mapped terrain features",
                "set up research equipment",
                "conducted gravitational experiments"
        };

        int taskIndex = ThreadLocalRandom.current().nextInt(tasks.length);
        System.out.println(astronautName + " " + tasks[taskIndex] +
                " (took " + explorationTime/1000.0 + " seconds)");
    }

}
