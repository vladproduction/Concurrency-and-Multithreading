package com.vladproduction.queue_samples.priorityBlockingQueue;

public class Person04 implements Comparable{

    private String name;
    private Integer age;


    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Person04 other = (Person04) o;
        return -(age - other.age);
    }
}
