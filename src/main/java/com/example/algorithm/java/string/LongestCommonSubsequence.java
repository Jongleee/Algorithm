package com.example.algorithm.java.string;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class LongestCommonSubsequence {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[] source = br.readLine().toCharArray();
        char[] target = br.readLine().toCharArray();

        int result = findLongestSubsequenceLength(source, target);
        System.out.println(result);
    }

    private static int findLongestSubsequenceLength(char[] source, char[] target) {
        int[] minIndex = new int[Math.min(source.length, target.length) + 1];
        Arrays.fill(minIndex, source.length);
        minIndex[0] = -1;

        for (char targetChar : target) {
            for (int len = minIndex.length - 2; len >= 0; len--) {
                int start = minIndex[len] + 1;
                int end = minIndex[len + 1];

                for (int i = start; i < end; i++) {
                    if (source[i] == targetChar) {
                        minIndex[len + 1] = i;
                        break;
                    }
                }
            }
        }

        for (int i = minIndex.length - 1; i >= 0; i--) {
            if (minIndex[i] < source.length) {
                return i;
            }
        }

        return 0;
    }
}

/*
ACAYKP
CAPCAK

4
*/