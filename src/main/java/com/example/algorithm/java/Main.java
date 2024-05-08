package com.example.algorithm.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int k;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim(), " ");
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        char[] line = br.readLine().toCharArray();
        int peopleNum = 0;
        for (int i = 0; i < n; i++) {
            if (line[i] == 'P' && isEatHamburger(i, line)) {
                peopleNum++;
            }
        }
        System.out.println(peopleNum);
    }

    private static boolean isEatHamburger(int index, char[] line) {
        int start = Math.max(0, index - k);
        int end = Math.min(n - 1, index + k);
        for (int i = start; i <= end; i++) {
            if (line[i] == 'H') {
                line[i] = 'T';
                return true;
            }
        }
        return false;
    }
}

/*
20 1
HHPHPPHHPPHPPPHPHPHP

8


20 2
HHHHHPPPPPHPHPHPHHHP

7
 */