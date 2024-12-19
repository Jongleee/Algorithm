package com.example.algorithm.java.prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PizzaSales {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int target = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        int[] a = new int[m * 2 + 1];
        for (int i = 1; i <= m; i++) {
            a[i] = Integer.parseInt(br.readLine());
            a[i + m] = a[i];
        }

        int[] b = new int[n * 2 + 1];
        for (int i = 1; i <= n; i++) {
            b[i] = Integer.parseInt(br.readLine());
            b[i + n] = b[i];
        }

        int[] sumA = calculatePrefixSum(a, m * 2);
        int[] sumB = calculatePrefixSum(b, n * 2);

        int[] arrA = calculateCombinations(sumA, target, m);
        int[] arrB = calculateCombinations(sumB, target, n);

        int answer = calculateAnswer(target, arrA, arrB);
        System.out.println(answer);
    }

    private static int[] calculatePrefixSum(int[] array, int size) {
        int[] prefixSum = new int[size + 1];
        prefixSum[1] = array[1];
        for (int i = 2; i <= size; i++) {
            prefixSum[i] = array[i] + prefixSum[i - 1];
        }
        return prefixSum;
    }

    private static int[] calculateCombinations(int[] prefixSum, int target, int size) {
        int[] combinations = new int[target + 1];
        for (int i = 1; i < size; i++) {
            for (int s = 1; s <= size; s++) {
                int value = prefixSum[s + i - 1] - prefixSum[s - 1];
                if (value > target)
                    continue;
                combinations[value]++;
            }
        }
        if (prefixSum[size] <= target)
            combinations[prefixSum[size]]++;
        return combinations;
    }

    private static int calculateAnswer(int target, int[] arrA, int[] arrB) {
        int answer = arrA[target] + arrB[target];
        for (int i = 1; i < target; i++) {
            answer += arrA[i] * arrB[target - i];
        }
        return answer;
    }
}

/*
7
5 3
2
2
1
7
2
6
8
3

5
*/