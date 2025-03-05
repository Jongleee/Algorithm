package com.example.algorithm.java.z;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrefixAndSuffix {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String input = br.readLine();
        int n = input.length();

        int[] zArray = computeZArray(input);
        List<Integer> borders = computeBorders(n, zArray);

        int[] countOccur = new int[n + 1];
        for (int i = 1; i < n; i++) {
            countOccur[zArray[i]]++;
        }

        for (int x = n - 1; x >= 1; x--) {
            countOccur[x] += countOccur[x + 1];
        }

        bw.write(borders.size() + "\n");
        for (int L : borders) {
            int count = (L == n) ? 1 : 1 + countOccur[L];
            bw.write(L + " " + count + "\n");
        }

        bw.flush();
    }

    private static int[] computeZArray(String s) {
        int n = s.length();
        int[] zArray = new int[n];
        int left = 0, right = 0;

        for (int i = 1; i < n; i++) {
            if (i <= right) {
                zArray[i] = Math.min(right - i + 1, zArray[i - left]);
            }
            while (i + zArray[i] < n && s.charAt(zArray[i]) == s.charAt(i + zArray[i])) {
                zArray[i]++;
            }
            if (i + zArray[i] - 1 > right) {
                left = i;
                right = i + zArray[i] - 1;
            }
        }
        return zArray;
    }

    private static List<Integer> computeBorders(int n, int[] zArray) {
        List<Integer> borders = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (i + zArray[i] == n) {
                borders.add(n - i);
            }
        }
        borders.add(n);
        Collections.sort(borders);
        return borders;
    }
}

/*
AAA

3  
1 3
2 2
3 1


ABACABA

3
1 4
3 2
7 1
*/