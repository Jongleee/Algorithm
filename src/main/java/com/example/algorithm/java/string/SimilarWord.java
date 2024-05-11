package com.example.algorithm.java.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimilarWord {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String first = br.readLine();
        int result = 0;

        for (int i = 0; i < n - 1; i++) {
            String str = br.readLine();
            int cnt = countCommonCharacters(first, str);
            if (isValidComparison(first, str, cnt)) {
                result++;
            }
        }
        System.out.println(result);
    }

    private static int countCommonCharacters(String first, String str) {
        int[] word = new int[26];
        for (int j = 0; j < first.length(); j++) {
            word[first.charAt(j) - 'A']++;
        }
        int cnt = 0;
        for (int j = 0; j < str.length(); j++) {
            if (word[str.charAt(j) - 'A'] > 0) {
                cnt++;
                word[str.charAt(j) - 'A']--;
            }
        }
        return cnt;
    }

    private static boolean isValidComparison(String first, String str, int cnt) {
        if (first.length() == str.length() && (first.length() == cnt || first.length() - 1 == cnt)) {
            return true;
        } else if (first.length() == str.length() - 1 && str.length() - 1 == cnt) {
            return true;
        } else return first.length() == str.length() + 1 && str.length() == cnt;
    }
}

/*
4
DOG
GOD
GOOD
DOLL

2
 */