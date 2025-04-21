package com.vladproduction.ch03_thread_safety.thread_safe_programming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImmutableExample {

    // Immutable class example
    public final class ImmutablePerson {
        private final String name;
        private final int age;
        private final List<String> hobbies;  // needs special handling

        public ImmutablePerson(String name, int age, List<String> hobbies) {
            this.name = name;
            this.age = age;
            // Defensive copy to ensure immutability
            this.hobbies = Collections.unmodifiableList(new ArrayList<>(hobbies));
        }

        public String getName() { return name; }
        public int getAge() { return age; }
        public List<String> getHobbies() { return hobbies; }
    }

}
