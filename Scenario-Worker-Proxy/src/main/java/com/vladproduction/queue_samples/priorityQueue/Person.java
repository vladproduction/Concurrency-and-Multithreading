package com.vladproduction.queue_samples.priorityQueue;

import java.util.Objects;

public class Person implements Comparable<Person>{

    private int id;
    private int age;
    private String name;
    private String position;

    public Person() { //serialization-deserialization
    }

    public Person(int id, int age, String name, String position) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.position = position;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Person person = (Person) object;
        return id == person.id && age == person.age && Objects.equals(name, person.name) && Objects.equals(position, person.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, name, position);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }


    @Override
    public int compareTo(Person person) {
        return Integer.compare(person.id, this.id); //for our priority in Queue (biggest id - remove)
    }
}
