package com.vladproduction.stream_api;

import java.util.*;
import java.util.stream.Collectors;

import static com.vladproduction.stream_api.Employee.Gender.FEMALE;
import static com.vladproduction.stream_api.Employee.Gender.MALE;

public class ReductionOperationWithCollect {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("David", "Clark", "Merry", "Sara", "Andy", "Merry");

        //collect toList()
        List<String> collectedNames = names.stream()
                .filter(n -> n.length() > 4)
                .collect(Collectors.toList());
        System.out.println("Collected List Names: " + collectedNames);

        //collect toSet()
        Set<String> collectedNamesSet = names.stream()
                .filter(n -> n.length() > 4)
                .collect(Collectors.toSet());
        System.out.println("Collected Set Names: " + collectedNamesSet);

        //joining
        String collectedNamesString = names.stream()
                .collect(Collectors.joining(", "));
        System.out.println("Collected String Names: " + collectedNamesString);

        //joining with prefix, suffix
        String collectedNamesStringPrefixSuffix = names.stream()
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Collected String Names [...]: " + collectedNamesStringPrefixSuffix);

        //grouping by
        Map<Integer, List<String>> namesGroupedByLength = names.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println("Names grouped by length: " + namesGroupedByLength);

        //partitioning by (return Map)
        Map<Boolean, List<String>> partitionedNamesByLength = names.stream()
                .collect(Collectors.partitioningBy(n -> n.length() > 4));
        System.out.println("Names partitioned by length: " + partitionedNamesByLength);

        //partitioning by (return Set)
        Map<Boolean, Set<String>> partitionedNamesByLengthSet = names.stream()
                .collect(Collectors.partitioningBy(n -> n.length() > 4, Collectors.toSet()));
        System.out.println("Names partitioned by length: " + partitionedNamesByLengthSet);

        //toCollection() (LinkedList returns example)
        List<String> collectedNamesLinkedList = names.stream()
                .filter(n -> n.length() > 4)
                .collect(Collectors.toCollection(LinkedList::new));
        System.out.println("Collected LinkedList Names: " + collectedNamesLinkedList);


    }
}
