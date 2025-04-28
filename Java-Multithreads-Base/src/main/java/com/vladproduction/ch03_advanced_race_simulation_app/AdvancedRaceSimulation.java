package com.vladproduction.ch03_advanced_race_simulation_app;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Advanced race simulation that demonstrates various threading concepts including
 * - Thread coordination with CountDownLatch
 * - Race statistics and leaderboard
 * - Configurable race parameters
 * - Thread-safe operations
 * - Proper resource handling
 */
public class AdvancedRaceSimulation {

    // Race configuration
    private static final int DEFAULT_DISTANCE = 100;
    private static final boolean DISPLAY_PROGRESS = true;
    private static final int PROGRESS_UPDATE_INTERVAL = 200; // ms

    // Thread-safe counter for position tracking
    private static final AtomicInteger finishPosition = new AtomicInteger(0);

    // List to store final race results
    private static final List<RaceResult> raceResults = Collections.synchronizedList(new ArrayList<>());

    // Countdown latch for coordinating race start (all racers start at the same time)
    private static CountDownLatch startSignal;

    // Countdown latch for tracking when all racers finish
    private static CountDownLatch finishSignal;

    public static void main(String[] args) {
        try {
            // Create the race configuration
            RaceConfig raceConfig = new RaceConfig.Builder()
                    .setDistance(DEFAULT_DISTANCE)
                    .setTrackCondition(TrackCondition.DRY)
                    .setWeatherFactor(WeatherFactor.SUNNY)
                    .build();

            System.out.println("===== ADVANCED RACE SIMULATION =====");
            System.out.println(raceConfig);
            System.out.println("===================================");

            // Create racers with different capabilities
            List<Racer> racers = createRacers(raceConfig);
            int racerCount = racers.size();

            // Initialize countdown latches
            startSignal = new CountDownLatch(1); // Single signal to start all racers
            finishSignal = new CountDownLatch(racerCount); // Wait for all racers to finish

            // Record start time
            long raceStartTime = System.currentTimeMillis();
            System.out.println("\nStarting race at: " + raceStartTime + "ms");

            // Start all racer threads
            for (Racer racer : racers) {
                racer.start();
            }

            // Small delay before starting race
            TimeUnit.SECONDS.sleep(1);
            System.out.println("\n3... 2... 1... GO!");

            // Signal race start to all waiting racers
            startSignal.countDown();

            // Wait for all racers to finish
            finishSignal.await();

            // Record finish time
            long raceFinishTime = System.currentTimeMillis();
            long totalRaceTime = raceFinishTime - raceStartTime;

            // Display race results
            System.out.println("\n===== RACE RESULTS =====");
            System.out.println("Total race time: " + formatTime(totalRaceTime));
            displayLeaderboard();
            System.out.println("========================");

            // Calculate and display statistics
            displayRaceStatistics();

        } catch (InterruptedException e) {
            System.err.println("Race was interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Creates a list of racers with different capabilities
     */
    private static List<Racer> createRacers(RaceConfig raceConfig) {
        List<Racer> racers = new ArrayList<>();

        // Add racers with different speeds and abilities
        racers.add(new Racer("Bolt", 12, 95, raceConfig, RacerType.SPRINTER));
        racers.add(new Racer("Johnson", 11, 90, raceConfig, RacerType.BALANCED));
        racers.add(new Racer("Liu", 10, 92, raceConfig, RacerType.TECHNICAL));
        racers.add(new Racer("Powell", 11.5, 85, raceConfig, RacerType.ENDURANCE));
        racers.add(new Racer("Blake", 11.2, 88, raceConfig, RacerType.SPRINTER));
        racers.add(new Racer("Gatlin", 10.8, 89, raceConfig, RacerType.BALANCED));

        return racers;
    }

    /**
     * Displays the race leaderboard sorted by finish position
     */
    private static void displayLeaderboard() {
        System.out.println("\nLEADERBOARD:");
        System.out.printf("%-5s %-12s %-10s %-10s %-15s%n", "POS", "NAME", "TIME", "SPEED", "TYPE");
        System.out.println("--------------------------------------------------");

        // Sort results by finish time
        Collections.sort(raceResults);

        // Display each result
        for (RaceResult result : raceResults) {
            System.out.printf("%-5d %-12s %-10s %-10.2f %-15s%n",
                    result.getPosition(),
                    result.getRacerName(),
                    formatTime(result.getFinishTime()),
                    result.getActualSpeed(),
                    result.getRacerType());
        }
    }

    /**
     * Calculates and displays various race statistics
     */
    private static void displayRaceStatistics() {
        if (raceResults.isEmpty()) {
            return;
        }

        double totalTime = 0;
        double fastestTime = Double.MAX_VALUE;
        double slowestTime = 0;
        String fastestRacer = "";
        String slowestRacer = "";

        for (RaceResult result : raceResults) {
            double time = result.getFinishTime();
            totalTime += time;

            if (time < fastestTime) {
                fastestTime = time;
                fastestRacer = result.getRacerName();
            }

            if (time > slowestTime) {
                slowestTime = time;
                slowestRacer = result.getRacerName();
            }
        }

        double averageTime = totalTime / raceResults.size();

        System.out.println("\n===== RACE STATISTICS =====");
        System.out.println("Total participants: " + raceResults.size());
        System.out.println("Average finish time: " + formatTime((long)averageTime));
        System.out.println("Fastest racer: " + fastestRacer + " (" + formatTime((long)fastestTime) + ")");
        System.out.println("Slowest racer: " + slowestRacer + " (" + formatTime((long)slowestTime) + ")");
        System.out.println("Time difference between first and last: " + formatTime((long)(slowestTime - fastestTime)));
        System.out.println("===========================");
    }

    /**
     * Formats time in milliseconds to a readable string (seconds.milliseconds)
     */
    private static String formatTime(long timeInMs) {
        DecimalFormat df = new DecimalFormat("0.000");
        return df.format(timeInMs / 1000.0) + "s";
    }

    /**
     * Represents an individual racer as a Thread
     */
    static class Racer extends Thread {
        private final String name;
        private final double baseSpeed; // in meters per second
        private final int efficiency; // percentage (0-100)
        private final RaceConfig raceConfig;
        private final RacerType type;

        private double distanceCovered = 0;
        private long startTime;
        private long finishTime;
        private double actualSpeed;

        /**
         * Creates a new racer
         *
         * @param name Racer's name
         * @param baseSpeed Base speed in meters per second
         * @param efficiency Efficiency percentage (0-100)
         * @param raceConfig Race configuration
         * @param type Racer's specialty type
         */
        public Racer(String name, double baseSpeed, int efficiency, RaceConfig raceConfig, RacerType type) {
            this.name = name;
            this.baseSpeed = baseSpeed;
            this.efficiency = efficiency;
            this.raceConfig = raceConfig;
            this.type = type;
        }

        @Override
        public void run() {
            try {
                // Wait for start signal
                System.out.println(name + " is ready at the starting line...");
                startSignal.await();

                // Record actual start time
                startTime = System.currentTimeMillis();

                // Calculate actual speed based on various factors
                actualSpeed = calculateActualSpeed();

                // Calculate expected finish time
                long expectedFinishTimeMs = (long)((raceConfig.getDistance() / actualSpeed) * 1000);

                // Show initial status
                System.out.println(name + " started with speed of " + String.format("%.2f", actualSpeed) + " m/s");

                // Race progress simulation with periodic updates
                long progressStart = System.currentTimeMillis();
                long lastUpdateTime = progressStart;

                while (distanceCovered < raceConfig.getDistance()) {
                    // Sleep a bit to avoid excessive CPU usage
                    Thread.sleep(PROGRESS_UPDATE_INTERVAL);

                    // Calculate elapsed time since last update
                    long currentTime = System.currentTimeMillis();
                    long elapsedSinceLastUpdate = currentTime - lastUpdateTime;

                    // Update distance covered
                    double distanceThisInterval = (actualSpeed * elapsedSinceLastUpdate) / 1000.0;
                    distanceCovered = Math.min(raceConfig.getDistance(), distanceCovered + distanceThisInterval);

                    // Display progress if enabled
                    if (DISPLAY_PROGRESS && elapsedSinceLastUpdate >= PROGRESS_UPDATE_INTERVAL) {
                        displayProgress(currentTime - progressStart);
                        lastUpdateTime = currentTime;
                    }

                    // Apply random race events (stumble, burst of speed, etc.)
                    applyRandomRaceEvents();
                }

                // Record finish time and position
                finishTime = System.currentTimeMillis() - startTime;
                int position = finishPosition.incrementAndGet();

                // Add result to the race results list
                raceResults.add(new RaceResult(name, finishTime, position, actualSpeed, type));

                // Announce finish
                System.out.println(name + " finished in position " + position + " with time " + formatTime(finishTime));

                // Signal that this racer has finished
                finishSignal.countDown();

            } catch (InterruptedException e) {
                System.err.println(name + " was interrupted during the race!");
                Thread.currentThread().interrupt();
                finishSignal.countDown(); // Ensure countdown happens even with interruption
            }
        }

        /**
         * Calculates actual speed based on various factors including:
         * - Base speed
         * - Efficiency
         * - Track condition
         * - Weather factor
         * - Racer type advantage
         */
        private double calculateActualSpeed() {
            double speed = baseSpeed * (efficiency / 100.0);

            // Apply track condition factor
            speed *= raceConfig.getTrackCondition().getSpeedFactor();

            // Apply weather factor
            speed *= raceConfig.getWeatherFactor().getSpeedFactor();

            // Apply racer type advantage
            if (type == RacerType.SPRINTER && raceConfig.getDistance() <= 200) {
                speed *= 1.1; // Sprinters get 10% boost in short races
            } else if (type == RacerType.ENDURANCE && raceConfig.getDistance() >= 800) {
                speed *= 1.15; // Endurance racers get 15% boost in long races
            } else if (type == RacerType.TECHNICAL &&
                    raceConfig.getTrackCondition() == TrackCondition.WET) {
                speed *= 1.08; // Technical racers handle wet conditions better
            }

            return speed;
        }

        /**
         * Simulates random events that may occur during the race
         */
        private void applyRandomRaceEvents() {
            // 5% chance of a random event occurring
            if (Math.random() < 0.05) {
                double eventEffect = 0;

                // Determine event type and effect
                double eventType = Math.random();

                if (eventType < 0.4) {
                    // Slight stumble (-5% speed)
                    eventEffect = -0.05;
                    // Technical racers recover better from stumbles
                    if (type == RacerType.TECHNICAL) {
                        eventEffect *= 0.5;
                    }
                } else if (eventType < 0.8) {
                    // Burst of speed (+5% speed)
                    eventEffect = 0.05;
                    // Sprinters get better bursts of speed
                    if (type == RacerType.SPRINTER) {
                        eventEffect *= 1.5;
                    }
                } else {
                    // Second wind (+8% speed)
                    eventEffect = 0.08;
                    // Endurance racers get better second winds
                    if (type == RacerType.ENDURANCE) {
                        eventEffect *= 1.5;
                    }
                }

                // Apply the effect to actual speed
                actualSpeed *= (1 + eventEffect);
            }
        }

        /**
         * Displays the current progress of the racer
         */
        private void displayProgress(long elapsedTime) {
            int percentComplete = (int)((distanceCovered / raceConfig.getDistance()) * 100);
            int progressBarLength = 20;
            int filledBlocks = (int)(progressBarLength * percentComplete / 100.0);

            StringBuilder progressBar = new StringBuilder("[");
            for (int i = 0; i < progressBarLength; i++) {
                progressBar.append(i < filledBlocks ? "=" : " ");
            }
            progressBar.append("]");

            System.out.printf("%-10s %3d%% %s %.1fm (%.2fm/s) %s%n",
                    name,
                    percentComplete,
                    progressBar.toString(),
                    distanceCovered,
                    actualSpeed,
                    formatTime(elapsedTime));
        }

        @Override
        public String toString() {
            return String.format("Racer {%-10s | Speed: %-4.2f m/s | Efficiency: %-3d%% | Type: %-10s}",
                    name, baseSpeed, efficiency, type);
        }
    }

    /**
     * Represents the final result of a racer
     */
    static class RaceResult implements Comparable<RaceResult> {
        private final String racerName;
        private final long finishTime;
        private final int position;
        private final double actualSpeed;
        private final RacerType racerType;

        public RaceResult(String racerName, long finishTime, int position, double actualSpeed, RacerType racerType) {
            this.racerName = racerName;
            this.finishTime = finishTime;
            this.position = position;
            this.actualSpeed = actualSpeed;
            this.racerType = racerType;
        }

        public String getRacerName() {
            return racerName;
        }

        public long getFinishTime() {
            return finishTime;
        }

        public int getPosition() {
            return position;
        }

        public double getActualSpeed() {
            return actualSpeed;
        }

        public RacerType getRacerType() {
            return racerType;
        }

        @Override
        public int compareTo(RaceResult other) {
            return Long.compare(this.finishTime, other.finishTime);
        }

        @Override
        public String toString() {
            return String.format("Result {%-10s | Time: %-10s | Position: %-2d | Speed: %-4.2f m/s}",
                    racerName, formatTime(finishTime), position, actualSpeed);
        }
    }

    /**
     * Enum representing different types of racers
     */
    enum RacerType {
        SPRINTER("Sprinter"),   // Good at short distances
        ENDURANCE("Endurance"), // Good at long distances
        TECHNICAL("Technical"), // Good at handling difficult conditions
        BALANCED("Balanced");   // Average at everything

        private final String description;

        RacerType(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    /**
     * Enum representing different track conditions
     */
    enum TrackCondition {
        DRY(1.0, "Dry"),       // Normal speed
        WET(0.85, "Wet"),      // 15% speed reduction
        MUDDY(0.75, "Muddy");  // 25% speed reduction

        private final double speedFactor;
        private final String description;

        TrackCondition(double speedFactor, String description) {
            this.speedFactor = speedFactor;
            this.description = description;
        }

        public double getSpeedFactor() {
            return speedFactor;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    /**
     * Enum representing different weather conditions
     */
    enum WeatherFactor {
        SUNNY(1.0, "Sunny"),             // Normal speed
        CLOUDY(0.98, "Cloudy"),          // 2% speed reduction
        LIGHT_RAIN(0.95, "Light Rain"),  // 5% speed reduction
        HEAVY_RAIN(0.85, "Heavy Rain"),  // 15% speed reduction
        WINDY(0.92, "Windy");            // 8% speed reduction

        private final double speedFactor;
        private final String description;

        WeatherFactor(double speedFactor, String description) {
            this.speedFactor = speedFactor;
            this.description = description;
        }

        public double getSpeedFactor() {
            return speedFactor;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    /**
     * Immutable class representing race configuration
     */
    static class RaceConfig {
        private final int distance;
        private final TrackCondition trackCondition;
        private final WeatherFactor weatherFactor;

        private RaceConfig(Builder builder) {
            this.distance = builder.distance;
            this.trackCondition = builder.trackCondition;
            this.weatherFactor = builder.weatherFactor;
        }

        public int getDistance() {
            return distance;
        }

        public TrackCondition getTrackCondition() {
            return trackCondition;
        }

        public WeatherFactor getWeatherFactor() {
            return weatherFactor;
        }

        @Override
        public String toString() {
            return String.format("Race Configuration:\n" +
                            "- Distance: %d meters\n" +
                            "- Track Condition: %s (Factor: %.2f)\n" +
                            "- Weather: %s (Factor: %.2f)",
                    distance,
                    trackCondition, trackCondition.getSpeedFactor(),
                    weatherFactor, weatherFactor.getSpeedFactor());
        }

        /**
         * Builder class for creating RaceConfig instances
         */
        static class Builder {
            private int distance = 100;
            private TrackCondition trackCondition = TrackCondition.DRY;
            private WeatherFactor weatherFactor = WeatherFactor.SUNNY;

            public Builder setDistance(int distance) {
                this.distance = distance;
                return this;
            }

            public Builder setTrackCondition(TrackCondition trackCondition) {
                this.trackCondition = trackCondition;
                return this;
            }

            public Builder setWeatherFactor(WeatherFactor weatherFactor) {
                this.weatherFactor = weatherFactor;
                return this;
            }

            public RaceConfig build() {
                return new RaceConfig(this);
            }
        }
    }

}
