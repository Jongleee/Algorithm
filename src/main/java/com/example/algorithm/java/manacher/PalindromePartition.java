package com.example.algorithm.java.manacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PalindromePartition {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        int length = input.length() * 2 - 1;
        char[] processed = new char[length];
        for (int i = 0, idx = 0; i < length; i += 2) {
            processed[i] = input.charAt(idx++);
        }

        int[] palindromeRadius = new int[length];
        computeManacher(processed, palindromeRadius, length);

        int[] minCuts = new int[length];
        minCuts[0] = 1;

        for (int i = 2; i < length; i += 2) {
            minCuts[i] = length;
            for (int j = 0; j <= i; j += 2) {
                int center = (i + j) / 2;
                int radius = (i - j) / 2;
                if (palindromeRadius[center] >= radius) {
                    minCuts[i] = Math.min(minCuts[i], j - 1 >= 0 ? minCuts[j - 2] + 1 : 1);
                }
            }
        }

        System.out.println(minCuts[length - 1]);
    }

    private static void computeManacher(char[] s, int[] radius, int len) {
        int right = -1, center = -1;
        for (int i = 0; i < len; i++) {
            if (right < i) {
                radius[i] = 0;
            } else {
                radius[i] = Math.min(right - i, radius[2 * center - i]);
            }
            while (i - radius[i] - 1 >= 0 && i + radius[i] + 1 < len
                    && s[i - radius[i] - 1] == s[i + radius[i] + 1]) {
                radius[i]++;
            }
            if (i + radius[i] > right) {
                right = i + radius[i];
                center = i;
            }
        }
    }
}

/*
BBCDDECAECBDABADDCEBACCCBDCAABDBADD

22


QWERTYTREWQWERT

5
*/