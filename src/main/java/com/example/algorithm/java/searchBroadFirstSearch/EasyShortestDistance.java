package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EasyShortestDistance {
    private static final int[] DX = {1, 0, -1, 0};
    private static final int[] DY = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);
        
        int[][] distance = new int[n][m];
        boolean[][] blocked = new boolean[n][m];
        int[] queueY = new int[n * m];
        int[] queueX = new int[n * m];
        int front = 0;
        int back = 0;
        
        StringBuilder sb = new StringBuilder((n * m) * 2);

        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < m; j++) {
                switch (line.charAt(j * 2)) {
                    case '0':
                        blocked[i][j] = true;
                        break;
                    case '2':
                        distance[i][j] = 1;
                        queueY[back] = i;
                        queueX[back++] = j;
                        break;
                    default:
                        break;
                }
            }
        }

        while (front < back) {
            int y = queueY[front];
            int x = queueX[front++];
            
            for (int d = 0; d < 4; d++) {
                int nextY = y + DY[d];
                int nextX = x + DX[d];
                
                if (nextY < 0 || nextX < 0 || nextY >= n || nextX >= m) continue;
                if (blocked[nextY][nextX] || distance[nextY][nextX] > 0) continue;
                
                distance[nextY][nextX] = distance[y][x] + 1;
                queueY[back] = nextY;
                queueX[back++] = nextX;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (distance[i][j] > 0) {
                    sb.append(distance[i][j] - 1).append(' ');
                } else if (blocked[i][j]) {
                    sb.append("0 ");
                } else {
                    sb.append("-1 ");
                }
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}

/*
15 15
2 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 0 0 0 0 1
1 1 1 1 1 1 1 1 1 1 0 1 1 1 1
1 1 1 1 1 1 1 1 1 1 0 1 0 0 0
1 1 1 1 1 1 1 1 1 1 0 1 1 1 1

0 1 2 3 4 5 6 7 8 9 10 11 12 13 14
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
4 5 6 7 8 9 10 11 12 13 14 15 16 17 18
5 6 7 8 9 10 11 12 13 14 15 16 17 18 19
6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
7 8 9 10 11 12 13 14 15 16 17 18 19 20 21
8 9 10 11 12 13 14 15 16 17 18 19 20 21 22
9 10 11 12 13 14 15 16 17 18 19 20 21 22 23
10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
11 12 13 14 15 16 17 18 19 20 0 0 0 0 25
12 13 14 15 16 17 18 19 20 21 0 29 28 27 26
13 14 15 16 17 18 19 20 21 22 0 30 0 0 0
14 15 16 17 18 19 20 21 22 23 0 31 32 33 34
 */