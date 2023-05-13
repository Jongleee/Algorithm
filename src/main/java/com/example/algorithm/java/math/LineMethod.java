package com.example.algorithm.java.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineMethod {
    public static int[] solution(int n, long k) {
        List<Integer> numbers = new ArrayList<>();
        int[] result = new int[n];
        long factorialNumber = 1;

        for (int i = 1; i <= n; i++) {
            factorialNumber *= i;
            numbers.add(i);
        }

        k--;

        int index = 0;
        while (n > 0) {
            factorialNumber /= n;
            result[index++] = numbers.remove((int) (k / factorialNumber));
            k %= factorialNumber;
            n--;
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(3, 5)));// [3,1,2]
    }
}
