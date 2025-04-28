package com.vladproduction.ch09_racing_daemon_non_daemon;

/**
 * CarRaceSimulation demonstrates daemon thread behavior in a race context.
 * Some cars are marked as "support vehicles" (daemon threads) while others are "race cars" (non-daemon).
 * When all race cars finish, the simulation ends regardless of support vehicles' status.
 */
public class CarRaceSimulation {

    public static void main(String[] args) {
        // Create race cars (non-daemon) and support vehicles (daemon)
        Car raceCar1 = new Car("Ferrari", 100, 11, false);
        Car raceCar2 = new Car("Mercedes", 100, 10, false);
        Car supportVehicle1 = new Car("Ambulance", 100, 9, true);
        Car supportVehicle2 = new Car("SafetyCar", 100, 8, true);

        System.out.println("=== RACE SIMULATION START ===");
        System.out.println("Race cars: Ferrari, Mercedes (non-daemon threads)");
        System.out.println("Support vehicles: Ambulance, SafetyCar (daemon threads)");
        System.out.println();

        long startTime = System.currentTimeMillis();
        System.out.println("Race started at: " + startTime + "ms");

        // Start all vehicles
        raceCar1.start();
        raceCar2.start();
        supportVehicle1.start();
        supportVehicle2.start();

        try {
            // Wait for race cars to finish
            raceCar1.join();
            raceCar2.join();

            // We don't join daemon threads - they will be terminated when non-daemon threads complete
            System.out.println("\nAll race cars have finished!");
            System.out.println("Note: Support vehicles may not complete their routes because they are daemon threads");

        } catch (InterruptedException e) {
            System.out.println("Race was interrupted unexpectedly");
        }

        long finishTime = System.currentTimeMillis();
        System.out.println("\nRace ended at: " + finishTime + "ms");
        System.out.println("Total race duration: " + (finishTime - startTime) + "ms");
        System.out.println("\n=== RACE SIMULATION COMPLETE ===");
    }

}
