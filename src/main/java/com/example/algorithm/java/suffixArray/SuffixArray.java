package com.example.algorithm.java.suffixArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class SuffixArray {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        char[] line = br.readLine().toCharArray();
        int n = line.length;

        int[] suffixArray = buildSuffixArray(line, n);
        for (int index : suffixArray) {
            sb.append(index + 1).append(' ');
        }

        sb.append("\nx ");
        int[] lcpArray = buildLCPArray(line, suffixArray, n);
        for (int i = 1; i < n; i++) {
            sb.append(lcpArray[i]).append(' ');
        }

        bw.write(sb.toString());
        bw.flush();
    }

    private static int[] buildSuffixArray(char[] line, int n) {
        int[] rank = new int[2 * n];
        int[] nextRank = new int[2 * n];
        int[] count = new int[Math.max(257, n + 1)];
        int[] temp = new int[n];
        int[] suffixArray = new int[n];

        for (int i = 0; i < n; i++) {
            suffixArray[i] = i;
            rank[i] = line[i];
        }

        for (int d = 1; d < n; d <<= 1) {
            countingSortSecond(rank, count, temp, n, d);
            countingSortFirst(rank, count, temp, suffixArray, n);

            updateRanks(rank, nextRank, suffixArray, n, d);

            if (rank[suffixArray[n - 1]] == n) {
                break;
            }
        }

        return suffixArray;
    }

    private static void countingSortSecond(int[] rank, int[] count, int[] temp, int n, int d) {
        Arrays.fill(count, 0);
        for (int i = 0; i < n; i++) {
            count[rank[i + d]]++;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            temp[--count[rank[i + d]]] = i;
        }
    }

    private static void countingSortFirst(int[] rank, int[] count, int[] temp, int[] suffixArray, int n) {
        Arrays.fill(count, 0);
        for (int i = 0; i < n; i++) {
            count[rank[i]]++;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            suffixArray[--count[rank[temp[i]]]] = temp[i];
        }
    }

    private static void updateRanks(int[] rank, int[] nextRank, int[] suffixArray, int n, int d) {
        nextRank[suffixArray[0]] = 1;
        for (int i = 1; i < n; i++) {
            int prev = suffixArray[i - 1];
            int curr = suffixArray[i];
            boolean isSame = rank[prev] == rank[curr] && rank[prev + d] == rank[curr + d];
            nextRank[curr] = nextRank[prev] + (isSame ? 0 : 1);
        }
        System.arraycopy(nextRank, 0, rank, 0, n);
    }

    private static int[] buildLCPArray(char[] line, int[] suffixArray, int n) {
        int[] lcp = new int[n];
        int[] inverseSuffixArray = new int[n];

        for (int i = 0; i < n; i++) {
            inverseSuffixArray[suffixArray[i]] = i;
        }

        int len = 0;
        for (int i = 0; i < n; i++) {
            if (inverseSuffixArray[i] == 0)
                continue;

            int j = suffixArray[inverseSuffixArray[i] - 1];
            while (i + len < n && j + len < n && line[i + len] == line[j + len]) {
                len++;
            }

            lcp[inverseSuffixArray[i]] = len;
            if (len > 0)
                len--;
        }

        return lcp;
    }
}

/*
abracadabra

11 8 1 4 6 9 2 5 7 10 3 
x 1 4 1 1 0 3 0 0 0 2   
*/