package com.vladproduction.stream_api;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.vladproduction.stream_api.Employee.Gender.*;

public class EmployeeAppWithReduceAndCollect {
    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee("David", 3000, MALE),
                new Employee("Merry", 2500, FEMALE),
                new Employee("Clark", 3500, MALE),
                new Employee("Andy", 4500, MALE),
                new Employee("Sara", 2000, FEMALE)
        );

        /////////////reduce()//////////

        //used reduce() method: (fine highest paid employee)
        Optional<Employee> highestPaidEmployee = employees.stream()
                .reduce((e1, e2) -> e1.getSalary() > e2.getSalary() ? e1 : e2);
        highestPaidEmployee.ifPresent(System.out::println);

        //reduce() method: (calculating total salary of all employees)
        Integer totalSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce(0, Integer::sum);
        System.out.println("Total salary: " + totalSalary);

        //reduce() method: (average employees salary)
        OptionalDouble average = OptionalDouble.of(employees.stream()
                .mapToInt(Employee::getSalary)
                .average()
                .orElseThrow(UnsupportedOperationException::new));
        System.out.println("Average salary: " + average.getAsDouble());

        // reduce() method: (combine names)
        String concatenatedNames = employees.stream()
                .map(Employee::getName)
                .reduce("", (n1, n2) -> n1 + " " + n2);
        System.out.println("Employees Names: " + concatenatedNames.trim());


        /////////////collect()//////////

        //example to calculate total salary and grouping by gender (Employee class is used)
        Map<Employee.Gender, Double> totalSalaryByGender = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.summingDouble(Employee::getSalary)));
        System.out.println("Total salary by gender: " + totalSalaryByGender);

        //how to use our own custom collector
        SalaryCollector salaryCollector = employees.stream()
                .map(Employee::getSalary)
                .collect(SalaryCollector::new, SalaryCollector::accept, SalaryCollector::combine);
        System.out.println("Total salary by custom collector: " + salaryCollector.getTotal());

        //we can group by gender with our custom collector:
        Map<Employee.Gender, SalaryCollector> totalSalaryByGenderUsingCustomCollector = employees.stream()
                .collect(
                        Collectors.groupingBy(
                                Employee::getGender,
                                Collectors.mapping(
                                        Employee::getSalary,
                                        Collector.of(
                                                SalaryCollector::new,
                                                SalaryCollector::accept,
                                                (s1, s2) -> {s1.combine(s2); return s1;}
                                        )
                                )
                        )
                );
        totalSalaryByGenderUsingCustomCollector
                .forEach((k, v)
                        -> System.out.println("Total salary by gender using custom collector: " + k + " -> " + v.getTotal()));
    }
}
