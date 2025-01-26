package com.vladproduction._14_livelock;

/**
 * In this example, we'll simulate two threads that are trying to pass each other but keep stepping back to avoid a collision.
 * However, because they keep modifying their behavior in response to each other, neither thread can proceed.
 * */
public class LivelockExample {
    static class Person extends Thread {
        private final String name;
        private final Object otherPerson;

        public Person(String name, Object otherPerson) {
            this.name = name;
            this.otherPerson = otherPerson;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println(name + " is trying to pass.");

                    synchronized (this) {
                        if (otherPerson instanceof Person) {
                            Person other = (Person) otherPerson;

                            synchronized (other) {
                                // Simulate checking and stepping back to avoid collision
                                System.out.println(name + " sees " + other.name + " and steps back.");
                                Thread.sleep(100); // Simulating the time taken to step back

                                // The other person steps back as well
                                other.stepBack();
                            }
                        }
                    }
                    // Sleep to allow the other thread to act
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void stepBack() {
            System.out.println(name + " is stepping back.");
        }
    }

    public static void main(String[] args) {
        Person person1 = new Person("Person 1", new Person("Person 2", null));
        Person person2 = new Person("Person 2", person1);

        // Start both persons, creating a livelock situation
        person1.start();
        person2.start();
    }
}
