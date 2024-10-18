package com.example.algorithm.java.dynamicProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CompositeFunctionAndQuery {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int numElements = Integer.parseInt(br.readLine());
        int[][] ancestors = new int[20][numElements + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 1; i <= numElements; i++) {
            ancestors[0][i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i < 20; i++) {
            for (int j = 1; j <= numElements; j++) {
                ancestors[i][j] = ancestors[i - 1][ancestors[i - 1][j]];
            }
        }

        int numQueries = Integer.parseInt(br.readLine());
        StringBuilder result = new StringBuilder();

        while (numQueries-- > 0) {
            st = new StringTokenizer(br.readLine());
            int steps = Integer.parseInt(st.nextToken());
            int startNode = Integer.parseInt(st.nextToken());

            for (int i = 0; steps > 0; i++) {
                if ((steps & 1) > 0) {
                    startNode = ancestors[i][startNode];
                }
                steps >>= 1;
            }
            result.append(startNode).append("\n");
        }

        System.out.println(result.toString());
        br.close();
    }
}

/*
5
3 3 5 4 3
5
1 1
2 1
11 3
1000 4
5 1

3
5
5
4
3
*/