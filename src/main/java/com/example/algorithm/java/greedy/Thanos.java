package com.example.algorithm.java.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Thanos {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        char[] s = br.readLine().toCharArray();
        int[] cnt = new int[2];

        for (char c : s) {
            int num = c - '0';
            cnt[num]++;
        }

        cnt[1] = (s.length - cnt[0]) / 2;
        cnt[0] /= 2;

        int deletedOnes = 0;
        int deletedZeros = 0;

        for (char c : s) {
            if (c == '1') {
                if (deletedOnes < cnt[1]) {
                    deletedOnes++;
                } else {
                    sb.append(1);
                }
            } else {
                if (deletedZeros < cnt[0]) {
                    sb.append(0);
                    deletedZeros++;
                }
            }
        }
        sb.append("\n");

        System.out.print(sb.toString());
    }
}

/*
1010

01


000011

001
 */
