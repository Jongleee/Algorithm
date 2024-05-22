package com.example.algorithm.java.queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class NthLargestNumber {
    static class Info implements Comparable<Info> {
        int row;
        int col;
        int value;

        public Info(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }

        @Override
        public int compareTo(Info other) {
            return Integer.compare(other.value, this.value);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        PriorityQueue<Info> pq = new PriorityQueue<>();
        for (int j = 0; j < n; j++) {
            pq.add(new Info(n - 1, j, matrix[n - 1][j]));
        }

        Info maxInfo = null;
        for (int i = 0; i < n; i++) {
            maxInfo = pq.poll();
            int row = maxInfo.row;
            int col = maxInfo.col;

            if (row > 0) {
                pq.add(new Info(row - 1, col, matrix[row - 1][col]));
            }
        }

        System.out.println(maxInfo.value);
    }
}

/*
5
12 7 9 15 5
13 8 11 19 6
21 10 26 31 16
48 14 28 35 25
52 20 32 41 49

35
 */