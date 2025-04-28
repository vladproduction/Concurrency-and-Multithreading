package com.vladproduction.ch29_phaser.team_collaboration;

import java.util.concurrent.*;

/**
 * This class demonstrates the Phaser synchronization construct for coordinating
 * a multiphase task with dynamic party registration and deregistration.
 * The scenario simulates a team working on multiple phases of a project
 * with team members joining and leaving at different phases.
 */
public class TeamCollaborationDemo {

    // Number of initial team members
    private static final int INITIAL_TEAM_SIZE = 4;
    // Number of project phases
    private static final int TOTAL_PHASES = 3;

    public static void main(String[] args) {
        System.out.println("Team Project Beginning - " + TOTAL_PHASES + " phases planned");

        // Create a phaser with 1 registered party (the main thread)
        // The phaser will have INITIAL_TEAM_SIZE + 1 parties initially
        Phaser phaser = new Phaser(1) {
            // Override onAdvance to provide phase-specific behavior
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("\n=========================================");
                System.out.println("PHASE " + phase + " COMPLETED by all participants");
                System.out.println("Registered parties remaining: " + registeredParties);

                // If this is the last phase or no parties are registered, terminate the phaser
                boolean isTerminated = (phase >= TOTAL_PHASES - 1) || (registeredParties == 0);

                if (isTerminated) {
                    System.out.println("Project completed successfully!");
                } else {
                    System.out.println("Moving to phase " + (phase + 1));
                }

                System.out.println("=========================================\n");
                return isTerminated;
            }
        };

        // Create a thread pool
        ExecutorService executorService = Executors.newCachedThreadPool();

        // Register initial team members and start their tasks
        for (int i = 1; i <= INITIAL_TEAM_SIZE; i++) {
            phaser.register(); // Register this party with the phaser
            final int memberId = i;

            executorService.submit(() -> {
                try {
                    // Each team member participates in one or more phases
                    TeamMember member = new TeamMember(memberId, phaser);
                    member.work();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Team member " + memberId + " was interrupted");
                }
            });
        }

        // Add a new team member after phase 0
        executorService.submit(() -> {
            try {
                // Wait for phase 0 to complete
                phaser.awaitAdvance(0);
                System.out.println("Specialist team member joining at phase 1");

                // Register with the phaser
                phaser.register();
                int memberId = INITIAL_TEAM_SIZE + 1;

                // New specialist joins for phase 1 and 2
                TeamMember specialistMember = new TeamMember(memberId, phaser);
                specialistMember.workSpecialist();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Specialist team member was interrupted");
            }
        });

        // Main thread arrives at the barrier, triggering phase 0 to start
        phaser.arriveAndDeregister();

        // Shutdown the executor service after all tasks complete
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(2, TimeUnit.MINUTES)) {
                System.err.println("Timeout! Some team members did not complete their tasks.");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted!");
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Represents a team member working on the project
     */
    static class TeamMember {
        private final int id;
        private final Phaser phaser;

        public TeamMember(int id, Phaser phaser) {
            this.id = id;
            this.phaser = phaser;
        }

        /**
         * Normal team member that works through all phases
         */
        public void work() throws InterruptedException {
            for (int phase = 0; phase < TOTAL_PHASES; phase++) {
                doPhaseWork(phase);

                // Check if we should leave the team
                if (phase == 1 && id % 2 == 0) {
                    System.out.println("Team member " + id + " is leaving the project after phase " + phase);
                    phaser.arriveAndDeregister(); // Leave the phaser
                    return;
                } else {
                    // Wait for all to finish this phase
                    System.out.println("Team member " + id + " waiting at phase barrier " + phase);
                    phaser.arriveAndAwaitAdvance();
                }
            }
        }

        /**
         * Specialist team member that works only in later phases
         */
        public void workSpecialist() throws InterruptedException {
            for (int phase = 1; phase < TOTAL_PHASES; phase++) {
                doPhaseWork(phase);

                // Wait for all to finish this phase
                System.out.println("Specialist " + id + " waiting at phase barrier " + phase);

                // For the last phase, we just arrive and deregister
                if (phase == TOTAL_PHASES - 1) {
                    phaser.arriveAndDeregister();
                } else {
                    phaser.arriveAndAwaitAdvance();
                }
            }
        }

        /**
         * Simulates work done in each phase
         */
        private void doPhaseWork(int phase) throws InterruptedException {
            System.out.println("Team member " + id + " starting work on phase " + phase);

            // Different phases have different work involved
            String[] workItems = {
                    "requirements gathering",
                    "design work",
                    "implementation",
                    "testing",
                    "documentation"
            };

            // Do multiple work items for this phase
            int itemCount = ThreadLocalRandom.current().nextInt(2, 5);
            for (int i = 0; i < itemCount; i++) {
                int workType = ThreadLocalRandom.current().nextInt(workItems.length);
                int workDuration = ThreadLocalRandom.current().nextInt(500, 2000);

                System.out.println("Team member " + id + " doing " + workItems[workType] +
                        " in phase " + phase);
                Thread.sleep(workDuration);
            }

            System.out.println("Team member " + id + " completed work on phase " + phase);
        }
    }

}
