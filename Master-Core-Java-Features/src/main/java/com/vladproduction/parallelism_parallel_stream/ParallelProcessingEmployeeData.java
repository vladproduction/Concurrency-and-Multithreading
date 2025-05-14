package com.vladproduction.parallelism_parallel_stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ParallelProcessingEmployeeData {
    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee("John", "IT", 70000),
                new Employee("Mary", "HR", 65000),
                new Employee("Bob", "IT", 75000),
                new Employee("Alice", "Finance", 80000),
                new Employee("David", "IT", 85000),
                new Employee("Susan", "HR", 60000),
                new Employee("Michael", "Finance", 90000),
                new Employee("Karen", "IT", 73000)
        );

        // Sequential processing time
        long start = System.currentTimeMillis();

        // Give 10% raise to IT department
        employees.stream()
                .filter(e -> e.getDepartment().equals("IT"))
                .forEach(e -> e.applySalaryIncrease(10));

        long sequentialTime = System.currentTimeMillis() - start;

        // Reset salaries
        employees = Arrays.asList(
                new Employee("John", "IT", 70000),
                new Employee("Mary", "HR", 65000),
                new Employee("Bob", "IT", 75000),
                new Employee("Alice", "Finance", 80000),
                new Employee("David", "IT", 85000),
                new Employee("Susan", "HR", 60000),
                new Employee("Michael", "Finance", 90000),
                new Employee("Karen", "IT", 73000)
        );

        // Parallel processing time
        start = System.currentTimeMillis();

        employees.parallelStream()
                .filter(e -> e.getDepartment().equals("IT"))
                .forEach(e -> e.applySalaryIncrease(10));

        long parallelTime = System.currentTimeMillis() - start;

        System.out.println("Sequential processing time: " + sequentialTime + " ms");
        System.out.println("Parallel processing time: " + parallelTime + " ms");

        // Group employees by department and calculate average salary
        Map<String, Double> avgSalaryByDept = employees.parallelStream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));

        System.out.println("\nAverage salary by department:");
        avgSalaryByDept.forEach((dept, avgSalary) ->
                System.out.println(dept + ": $" + String.format("%.2f", avgSalary)));

        // Print all employees
        System.out.println("\nEmployee details:");
        employees.forEach(System.out::println);
    }

    static class Employee {
        private final String name;
        private final String department;
        private double salary;

        public Employee(String name, String department, double salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public String getDepartment() {
            return department;
        }

        public double getSalary() {
            return salary;
        }

        public void applySalaryIncrease(double percentage){
            //simulating CPU intensive work:
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            this.salary *= (1 + percentage / 100); //will increase salary by percentage as a parameter
        }

        @Override
        public String toString() {
            return name + " (" + department + "): $" + String.format("%.2f", salary);
        }
    }


}
