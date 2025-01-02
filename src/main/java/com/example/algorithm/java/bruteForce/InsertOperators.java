package com.example.algorithm.java.bruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class InsertOperators {
    private static class Result {
        int min;
        int max;

        Result(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] numbers = new int[n];
        int[] operators = new int[4];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 4; i++) {
            operators[i] = Integer.parseInt(st.nextToken());
        }

        Result result = new Result(Integer.MAX_VALUE, Integer.MIN_VALUE);
        calculate(numbers, operators, 1, numbers[0], result);

        System.out.println(result.max);
        System.out.println(result.min);
    }

    private static void calculate(int[] numbers, int[] operators, int idx, int current, Result result) {
        if (idx == numbers.length) {
            result.max = Math.max(result.max, current);
            result.min = Math.min(result.min, current);
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (operators[i] > 0) {
                operators[i]--;
                int next = applyOperation(current, numbers[idx], i);
                calculate(numbers, operators, idx + 1, next, result);
                operators[i]++;
            }
        }
    }

    private static int applyOperation(int a, int b, int operator) {
        switch (operator) {
            case 0:
                return a + b;
            case 1:
                return a - b;
            case 2:
                return a * b;
            case 3:
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }
}

/*
2
5 6
0 0 1 0

30
30


6
1 2 3 4 5 6
2 1 1 1

54
-24
*/