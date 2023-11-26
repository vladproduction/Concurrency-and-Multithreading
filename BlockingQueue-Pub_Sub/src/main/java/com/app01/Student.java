package com.app01;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Student {

    private String name;
    private Integer age;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        support.firePropertyChange("name", this.name, name);
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        support.firePropertyChange("age", this.age, age);
        this.age = age;
    }

    public void addSubscriber(PropertyChangeListener subscriber){
        support.addPropertyChangeListener(subscriber);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
