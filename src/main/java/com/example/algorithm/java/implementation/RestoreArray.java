package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RestoreArray {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int height = Integer.parseInt(st.nextToken());
        int width = Integer.parseInt(st.nextToken());
        int offsetX = Integer.parseInt(st.nextToken());
        int offsetY = Integer.parseInt(st.nextToken());
        StringBuilder sb = new StringBuilder();

        int[][] a = new int[height][width];
        for (int i = 0; i < height; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < width; j++) {
                int value = Integer.parseInt(st.nextToken());
                if (i >= offsetX && j >= offsetY) {
                    a[i][j] = value - a[i - offsetX][j - offsetY];
                } else {
                    a[i][j] = value;
                }
                sb.append(a[i][j]).append(" ");
            }
            sb.append("\n");
        }

        System.out.print(sb);
    }
}

/*
2 4 1 1
1 2 3 4 0
5 7 9 11 4
0 5 6 7 8

1 2 3 4
5 6 7 8


3 3 2 1
1 2 3 0
4 5 6 0
7 9 11 3
0 4 5 6
0 7 8 9

1 2 3
4 5 6
7 8 9
*/