package com.example.algorithm.java.slidingWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RoundSushi {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        int[] sushi = new int[n];
        for (int i = 0; i < n; i++) {
            sushi[i] = Integer.parseInt(br.readLine());
        }

        System.out.println(findMaxSushiVariety(sushi, n, d, k, c));
    }

    private static int findMaxSushiVariety(int[] sushi, int n, int d, int k, int c) {
        int[] count = new int[d + 1];
        int currentVariety = 0;
        int maxVariety = 0;

        for (int i = 0; i < k; i++) {
            if (count[sushi[i]] == 0) {
                currentVariety++;
            }
            count[sushi[i]]++;
        }

        maxVariety = currentVariety;

        for (int i = 0; i < n; i++) {
            if (maxVariety <= currentVariety) {
                if (count[c] == 0) {
                    maxVariety = currentVariety + 1;
                } else {
                    maxVariety = currentVariety;
                }
            }

            int endIndex = (i + k) % n;
            count[sushi[i]]--;
            if (count[sushi[i]] == 0) {
                currentVariety--;
            }
            if (count[sushi[endIndex]] == 0) {
                currentVariety++;
            }
            count[sushi[endIndex]]++;
        }

        return maxVariety;
    }
}

/*
8 30 4 30
7
9
7
30
2
7
9
25

5


8 50 4 7
2
7
9
25
7
9
7
30

4
 */