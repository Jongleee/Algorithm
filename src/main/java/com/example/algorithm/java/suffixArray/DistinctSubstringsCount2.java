package com.example.algorithm.java.suffixArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DistinctSubstringsCount2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        char[] line = br.readLine().toCharArray();
        int n = line.length;

        int[] suffixArray = getSuffixArray(line, n);
        int[] lcpArray = getLcpArray(line, suffixArray, n);

        long result = calculateDistinctSubstrings(suffixArray, lcpArray);

        bw.write(Long.toString(result));
        bw.flush();
    }

    private static int[] getSuffixArray(char[] line, int n) {
        int[] sa = new int[n], rank = new int[2 * n], newRank = new int[2 * n];
        int[] count, index = new int[n];
        int maxChar = Math.max(257, n + 1);

        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = line[i];
        }

        for (int d = 1; d < n; d <<= 1) {
            count = new int[maxChar];
            for (int i = 0; i < n; i++)
                count[rank[i + d]]++;
            for (int i = 1; i < maxChar; i++)
                count[i] += count[i - 1];
            for (int i = n - 1; i >= 0; i--)
                index[--count[rank[i + d]]] = i;

            updateSuffixArray(n, sa, rank, newRank, index, maxChar, d);

            for (int i = 0; i < n; i++)
                rank[i] = newRank[i];
            if (rank[sa[n - 1]] == n)
                break;
        }

        return sa;
    }

    private static void updateSuffixArray(int n, int[] sa, int[] rank, int[] newRank, int[] index, int maxChar, int d) {
        int[] count = new int[maxChar];
        for (int i = 0; i < n; i++)
            count[rank[i]]++;
        for (int i = 1; i < maxChar; i++)
            count[i] += count[i - 1];
        for (int i = n - 1; i >= 0; i--)
            sa[--count[rank[index[i]]]] = index[i];

        newRank[sa[0]] = 1;
        for (int i = 1; i < n; i++) {
            boolean isDifferent = rank[sa[i - 1]] < rank[sa[i]] ||
                    (rank[sa[i - 1]] == rank[sa[i]] && rank[sa[i - 1] + d] < rank[sa[i] + d]);
            newRank[sa[i]] = newRank[sa[i - 1]] + (isDifferent ? 1 : 0);
        }
    }

    private static int[] getLcpArray(char[] line, int[] sa, int n) {
        int[] lcp = new int[n], isa = new int[n];
        for (int i = 0; i < n; i++)
            isa[sa[i]] = i;

        for (int k = 0, i = 0; i < n; i++) {
            if (isa[i] > 0) {
                int j = sa[isa[i] - 1];
                while (Math.max(i + k, j + k) < n && line[i + k] == line[j + k])
                    k++;
                lcp[isa[i]] = k;
                if (k > 0)
                    k--;
            }
        }

        return lcp;
    }

    private static long calculateDistinctSubstrings(int[] sa, int[] lcp) {
        long totalSubstrings = 0;
        for (int i : sa)
            totalSubstrings += i + 1;
        for (int i : lcp)
            totalSubstrings -= i;
        return totalSubstrings;
    }
}

/*
ababc

12
*/