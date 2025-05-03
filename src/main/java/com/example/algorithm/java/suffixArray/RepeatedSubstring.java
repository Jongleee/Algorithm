package com.example.algorithm.java.suffixArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class RepeatedSubstring {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        char[] line = br.readLine().toCharArray();

        int[] suffixArray = buildSuffixArray(line, n);
        int[] lcpArray = buildLCPArray(line, suffixArray, n);

        Arrays.sort(lcpArray);
        bw.write(Integer.toString(lcpArray[n - 1]));
        bw.flush();
    }

    private static int[] buildSuffixArray(char[] line, int n) {
        int[] sa = new int[n];
        int[] r = new int[2 * n];
        int[] nr = new int[2 * n];
        int[] cnt = new int[Math.max(257, n + 1)];
        int[] idx = new int[n];

        for (int i = 0; i < n; i++) {
            sa[i] = i;
            r[i] = line[i];
        }

        for (int d = 1; d < n; d <<= 1) {
            countingSortSecondKey(r, cnt, idx, n, d);
            countingSortFirstKey(r, cnt, sa, idx, n);
            updateRanks(r, nr, sa, d, n);
            if (r[sa[n - 1]] == n)
                break;
        }

        return sa;
    }

    private static void countingSortSecondKey(int[] r, int[] cnt, int[] idx, int n, int d) {
        Arrays.fill(cnt, 0);
        for (int i = 0; i < n; i++)
            cnt[r[i + d]]++;
        for (int i = 1; i < cnt.length; i++)
            cnt[i] += cnt[i - 1];
        for (int i = n - 1; i >= 0; i--)
            idx[--cnt[r[i + d]]] = i;
    }

    private static void countingSortFirstKey(int[] r, int[] cnt, int[] sa, int[] idx, int n) {
        Arrays.fill(cnt, 0);
        for (int i = 0; i < n; i++)
            cnt[r[i]]++;
        for (int i = 1; i < cnt.length; i++)
            cnt[i] += cnt[i - 1];
        for (int i = n - 1; i >= 0; i--)
            sa[--cnt[r[idx[i]]]] = idx[i];
    }

    private static void updateRanks(int[] r, int[] nr, int[] sa, int d, int n) {
        nr[sa[0]] = 1;
        for (int i = 1; i < n; i++) {
            boolean same = r[sa[i - 1]] == r[sa[i]] &&
                    r[sa[i - 1] + d] == r[sa[i] + d];
            nr[sa[i]] = nr[sa[i - 1]] + (same ? 0 : 1);
        }
        System.arraycopy(nr, 0, r, 0, n);
    }

    private static int[] buildLCPArray(char[] line, int[] sa, int n) {
        int[] lcp = new int[n];
        int[] isa = new int[n];

        for (int i = 0; i < n; i++) {
            isa[sa[i]] = i;
        }

        int k = 0;
        for (int i = 0; i < n; i++) {
            if (isa[i] == 0)
                continue;
            int j = sa[isa[i] - 1];
            while (i + k < n && j + k < n && line[i + k] == line[j + k])
                k++;
            lcp[isa[i]] = k;
            if (k > 0)
                k--;
        }
        return lcp;
    }
}

/*
28
tellmetellmetetetetetetellme

11


5
jykim

0
*/