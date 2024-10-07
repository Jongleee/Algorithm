package com.example.algorithm.java.longestCommonPrefix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Cubeditor {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputString = br.readLine();

        String[] suffixes = new String[inputString.length()];
        for (int i = 0; i < inputString.length(); i++) {
            suffixes[i] = inputString.substring(i);
        }

        Arrays.sort(suffixes);

        int maxCommonLength = findMaxCommonPrefix(suffixes);
        System.out.println(maxCommonLength);
    }

    private static int findMaxCommonPrefix(String[] sortedSuffixes) {
        int maxLength = -1;

        for (int i = 1; i < sortedSuffixes.length; i++) {
            int commonLength = getCommonPrefixLength(sortedSuffixes[i - 1], sortedSuffixes[i]);
            if (commonLength > maxLength) {
                maxLength = commonLength;
            }
        }

        return maxLength;
    }

    private static int getCommonPrefixLength(String first, String second) {
        int commonCount = 0;
        int minLength = Math.min(first.length(), second.length());

        while (commonCount < minLength && first.charAt(commonCount) == second.charAt(commonCount)) {
            commonCount++;
        }

        return commonCount;
    }
}

/*
abcdabcabb

3


abcdefghijklmn

0


abcabcabc

6
*/