package com.example.algorithm.java.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Stream {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");
        
        List<String> result = list.stream()
                                 .filter(s -> s.length() >= 5)
                                 .collect(Collectors.toList());
        
        System.out.println(result);
    }
}

