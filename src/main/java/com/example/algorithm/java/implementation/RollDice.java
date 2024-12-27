package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RollDice {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int[] dr = { 0, 0, 0, -1, 1 };
        int[] dc = { 0, 1, -1, 0, 0 };

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] matrix = new int[n][m];
        int[] dice = { 1, 3, 4, 2, 5, 6 };
        int[] value = new int[7];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                matrix[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < k; i++) {
            int d = Integer.parseInt(st.nextToken());

            int nr = r + dr[d];
            int nc = c + dc[d];

            if (nr < 0 || nr >= n || nc < 0 || nc >= m) {
                continue;
            }

            r = nr;
            c = nc;
            moveDice(dice, d);

            if (matrix[r][c] == 0) {
                matrix[r][c] = value[dice[0]];
            } else {
                value[dice[0]] = matrix[r][c];
                matrix[r][c] = 0;
            }

            sb.append(value[dice[5]]).append("\n");
        }
        System.out.print(sb);
    }

    private static void moveDice(int[] dice, int direction) {
        int temp = dice[direction];
        dice[direction] = dice[0];
        dice[0] = 7 - temp;
        dice[direction + (direction % 2 == 0 ? -1 : 1)] = dice[5];
        dice[5] = temp;
    }
}

/*
3 3 1 1 9
1 2 3
4 0 5
6 7 8
1 3 2 2 4 4 1 1 3

0
0
0
3
0
1
0
6
0
*/