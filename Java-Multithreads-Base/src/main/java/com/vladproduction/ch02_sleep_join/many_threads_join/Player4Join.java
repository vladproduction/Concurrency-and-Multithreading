package com.vladproduction.ch02_sleep_join.many_threads_join;

/**
 * Represents a player in a race as a Thread.
 * Each player runs at a specific speed over a set distance.
 */
class Player4Join extends Thread {
    private final String playerName; // Name identifier for the player
    private final int distance;      // Distance to travel in meters
    private final int speed;         // Speed in meters per second

    /**
     * Constructor to initialize player properties
     *
     * @param playerName The name of the player
     * @param distance   The distance to travel in meters
     * @param speed      The speed in meters per second
     */
    public Player4Join(String playerName, int distance, int speed) {
        this.playerName = playerName;
        this.distance = distance;
        this.speed = speed;
    }

    /**
     * The thread's execution method that calls makeAction()
     */
    @Override
    public void run() {
        makeAction();
    }

    /**
     * Simulates a player running the race.
     * Uses a busy-wait approach to simulate the time it takes to complete the race.
     */
    public void makeAction() {
        System.out.println("\tSTART: " + playerName);

        // Calculate how long this player should take to finish based on distance and speed
        // Convert to milliseconds by × 1000
        long timeToAction = (distance / speed) * 1000L;
        long playerStartTime = System.currentTimeMillis();

        System.out.println("\t\texpected time: " + timeToAction + "ms");

        // Busy-wait until the calculated race time has elapsed
        // Note: This is CPU-intensive; Thread.sleep() would be more efficient
        while ((System.currentTimeMillis() - playerStartTime) < timeToAction) {
            // Busy waiting - intentionally empty
        }

        System.out.println("\tFINISH: " + playerName);
    }

    /**
     * Returns a string representation of the player
     */
    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", distance=" + distance +
                ", speed=" + speed +
                '}';
    }
}
