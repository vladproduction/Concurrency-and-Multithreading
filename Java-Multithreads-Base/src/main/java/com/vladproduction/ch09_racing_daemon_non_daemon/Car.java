package com.vladproduction.ch09_racing_daemon_non_daemon;


/**
 * Car class represents a vehicle in the race simulation.
 * It can be configured as either a race car (non-daemon thread) or
 * a support vehicle (daemon thread).
 */
public class Car extends Thread {

    private final String model;                     // Car model/name
    private final int distance;                     // Distance to travel in arbitrary units
    private final int speed;                        // Speed in arbitrary units
    private final boolean isSupportCar;             // Whether this is a support vehicle (daemon)

    /**
     * Creates a new car for the race simulation.
     *
     * @param model     The car model or name
     * @param distance  Distance the car needs to travel
     * @param speed     Speed of the car
     * @param isSupportCar If true, this car is a support vehicle (daemon thread)
     */
    public Car(String model, int distance, int speed, boolean isSupportCar) {
        this.model = model;
        this.distance = distance;
        this.speed = speed;
        this.isSupportCar = isSupportCar;

        //set daemon status based on car type
        this.setDaemon(isSupportCar);

        // Set a descriptive thread name
        this.setName(isSupportCar ? "Support-"  + model : "RaceCar-" + model);
    }

    @Override
    public void run() {
        // Simulate the car driving its route
        driveRoute();
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", distance=" + distance +
                ", speed=" + speed +
                ", isSupport=" + isSupportCar +
                '}';
    }

    /**
     * Simulates the car driving its designated route.
     * The time it takes is based on distance/speed.
     */
    private void driveRoute() {
        String carType = isSupportCar ? "Support" : "Race car";
        System.out.println(carType + " " + model + " starting its route");

        // Calculate how long this car should take to complete its route
        long drivingTime = (distance / speed) * 1000L;
        long startTime = System.currentTimeMillis();

        System.out.println("\t" + model + " should take " + drivingTime + "ms to finish");

        // Busy wait to simulate driving (in real code, you would use Thread.sleep() instead)
        while ((System.currentTimeMillis() - startTime) < drivingTime) {
            // Just waiting...

            // Optional: Add checkpoints
            if ((System.currentTimeMillis() - startTime) % 2000 == 0) {
                System.out.println("\t" + model + " checkpoint at " +
                        ((System.currentTimeMillis() - startTime) / 1000) + "s");

                // Small sleep to avoid printing the checkpoint message multiple times
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        System.out.println(carType + " " + model + " finished its route!");

    }
}
