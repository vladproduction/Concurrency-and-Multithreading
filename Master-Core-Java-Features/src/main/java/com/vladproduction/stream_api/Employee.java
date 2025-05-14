package com.vladproduction.stream_api;

public class Employee {

    private String name;
    private int salary;
    public enum Gender{MALE, FEMALE}
    private Gender gender;

    public Employee(String name, int salary, Gender gender){
        this.name = name;
        this.salary = salary;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Employee emp){
            return this.name.equals(emp.name) && this.salary == emp.salary && this.gender == emp.gender;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.name.hashCode() + this.salary + this.gender.hashCode();
    }

    @Override
    public String toString(){
        return String.format("Name: %s, Salary: %d, Gender: %s", this.name, this.salary, this.gender);
    }


}
