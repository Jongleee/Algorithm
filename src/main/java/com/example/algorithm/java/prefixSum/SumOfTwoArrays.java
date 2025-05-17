package com.example.algorithm.java.prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class SumOfTwoArrays {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int target = Integer.parseInt(br.readLine());
        int n = Integer.parseInt(br.readLine());
        int[] prefixA = getPrefixSumArray(br, n);

        int m = Integer.parseInt(br.readLine());
        int[] prefixB = getPrefixSumArray(br, m);

        Map<Integer, Integer> sumCountMap = buildSubArraySumCount(prefixB, m);

        long result = countMatchingSubArrays(prefixA, n, sumCountMap, target);
        System.out.println(result);
    }

    private static int[] getPrefixSumArray(BufferedReader br, int size) throws IOException {
        int[] prefixSum = new int[size + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= size; i++) {
            int value = Integer.parseInt(st.nextToken());
            prefixSum[i] = prefixSum[i - 1] + value;
        }
        return prefixSum;
    }

    private static Map<Integer, Integer> buildSubArraySumCount(int[] prefix, int length) {
        Map<Integer, Integer> countMap = new HashMap<>(1000000);
        for (int i = length; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                int sum = prefix[i] - prefix[j];
                countMap.put(sum, countMap.getOrDefault(sum, 0) + 1);
            }
        }
        return countMap;
    }

    private static long countMatchingSubArrays(int[] prefix, int length, Map<Integer, Integer> countMap, int target) {
        long totalCount = 0;
        for (int i = length; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                int sum = prefix[i] - prefix[j];
                int complement = target - sum;
                totalCount += countMap.getOrDefault(complement, 0);
            }
        }
        return totalCount;
    }
}

/*
5
4      
1 3 1 2
3
1 3 2

7
*/