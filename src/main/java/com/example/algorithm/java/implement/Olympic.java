package com.example.algorithm.java.implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Olympic {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][4];

        int idx = -1;
        int cnt = 1;

        for (int i = 0; i < n; i++) {
            StringTokenizer st1 = new StringTokenizer(br.readLine());
            for(int j=0;j<4;j++) {
				map[i][j] = Integer.parseInt(st1.nextToken());
			}
            if (map[i][0] == k) {
                idx = i;
            }
        }
        int a = map[idx][1];
        int b = map[idx][2];
        int c = map[idx][3];

        for (int i = 0; i < n; i++) {
            if (i == idx)
                continue;
            if (map[i][1] > a || (map[i][1] == a && map[i][2] > b)
                    || (map[i][1] == a && map[i][2] == b && map[i][3] > c)) {
                cnt++;
            }
        }

        System.out.println(cnt);
    }
}
/*
4 3
1 1 2 0
2 0 1 0
3 0 1 0
4 0 0 1

2

4 2
1 3 0 0
3 0 0 2
4 0 2 0
2 0 2 0

2
 */