package com.example.algorithm.java.practice.sort;

import java.util.Arrays;

public class BiggestNumber {

    public static String solution(int[] numbers) {
        String[] arr = Arrays.stream(numbers)
                             .mapToObj(String::valueOf)
                             .toArray(String[]::new);

        Arrays.sort(arr, (s1, s2) -> (s2 + s1).compareTo(s1 + s2));

        if (arr[0].equals("0")) {
            return "0";
        }

        return String.join("", arr);
    }

    public static void main(String[] args) {
        int[] n1 = {6, 10, 2};
        int[] n2 = {3, 30, 34, 5, 9};

        System.out.println(solution(n1)); // 6210
        System.out.println(solution(n2)); // 9534330
    }
}
