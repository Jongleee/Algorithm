package com.example.algorithm.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static final int MAX_DISTANCE = 60000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int cityCount = Integer.parseInt(br.readLine());
        int busCount = Integer.parseInt(br.readLine());

        int[][] distances = new int[cityCount + 1][cityCount + 1];

        for (int i = 1; i <= cityCount; i++) {
            Arrays.fill(distances[i], MAX_DISTANCE);
            distances[i][i] = 0;
        }

        for (int i = 0; i < busCount; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int travelCost = Integer.parseInt(st.nextToken());

            distances[from][to] = Math.min(distances[from][to], travelCost);
        }

        for (int via = 1; via <= cityCount; via++) {
            for (int from = 1; from <= cityCount; from++) {
                for (int to = 1; to <= cityCount; to++) {
                    distances[from][to] = Math.min(distances[from][to],
                            distances[from][via] + distances[via][to]);
                }
            }
        }

        for (int i = 1; i <= cityCount; i++) {
            for (int j = 1; j <= cityCount; j++) {
                bw.write((distances[i][j] < MAX_DISTANCE ? distances[i][j] : 0) + " ");
            }
            bw.write("\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}

/*
5
14
1 2 2
1 3 3
1 4 1
1 5 10
2 4 2
3 4 1
3 5 1
4 5 3
3 5 10
3 1 8
1 4 2
5 1 7
3 4 2
5 2 4

0 2 3 1 4
12 0 15 2 5
8 5 0 1 1
10 7 13 0 3
7 4 10 6 0
*/