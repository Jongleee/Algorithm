package com.example.algorithm.java.prefixSum;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Parcel {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int w = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        int[] numbers = new int[n];
        boolean[] possibleSums = new boolean[400001];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }

        boolean isPossible = canFormTargetSum(w, numbers, possibleSums, n);

        bw.write(isPossible ? "YES\n" : "NO\n");
        bw.flush();
        bw.close();
        br.close();
    }

    private static boolean canFormTargetSum(int w, int[] numbers, boolean[] possibleSums, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int currentSum = numbers[i] + numbers[j];
                if (currentSum >= w || w - currentSum > 400000)
                    continue;
                if (possibleSums[w - currentSum])
                    return true;
            }
            for (int j = 0; j < i; j++) {
                possibleSums[numbers[i] + numbers[j]] = true;
            }
        }
        return false;
    }
}

/*
10 6
5 10 7 3 2 1

NO


21 7
10 1 4 6 2 8 5

YES
*/