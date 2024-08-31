package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class WordScramble {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= t; ++tc) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String line1 = st.nextToken();
            String line2 = st.nextToken();
            String line3 = st.nextToken();
            sb.append("Data set ").append(tc).append(": ")
                    .append(isInterleaving(line1, line2, line3) ? "yes\n" : "no\n");
        }

        System.out.print(sb);
    }

    private static boolean isInterleaving(String line1, String line2, String line3) {
        int[] charCount = new int[256];
        int n = line1.length();
        int m = line2.length();
        int l = line3.length();

        if (n + m != l)
            return false;

        for (char c : line1.toCharArray())
            charCount[c]++;
        for (char c : line2.toCharArray())
            charCount[c]++;
        for (char c : line3.toCharArray())
            charCount[c]--;

        for (int count : charCount) {
            if (count != 0)
                return false;
        }

        return isSubsequence(line1, line3) && isSubsequence(line2, line3);
    }

    private static boolean isSubsequence(String s1, String s2) {
        int idx = 0;
        for (char c : s2.toCharArray()) {
            if (idx < s1.length() && c == s1.charAt(idx)) {
                idx++;
            }
        }
        return idx == s1.length();
    }
}

/*
3
cat tree tcraete
cat tree catrtee
cat tree cttaree

Data set 1: yes
Data set 2: yes
Data set 3: no
*/