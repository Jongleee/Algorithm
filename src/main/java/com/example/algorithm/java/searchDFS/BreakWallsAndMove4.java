package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BreakWallsAndMove4 {
    static int[][] map;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        StringBuilder sb = new StringBuilder((n + 5) * m);

        map = new int[n + 2][m + 2];
        int[][] answer = new int[n + 2][m + 2];
        for (int i = 0; i < n + 2; i++) {
            map[i][0] = 1;
            map[i][m + 1] = 1;
        }
        for (int i = 0; i < m + 2; i++) {
            map[0][i] = 1;
            map[n + 1][i] = 1;
        }

        for (int i = 1; i <= n; i++) {
            String s = br.readLine();
            for (int j = 1; j <= m; j++) {
                map[i][j] = s.charAt(j - 1) - '0';
            }
        }

        ArrayList<Integer> area = new ArrayList<>();
        area.add(0);
        area.add(0);
        int idx = 2;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (map[i][j] == 0) {
                    int size = search(idx++, i, j);
                    area.add(size);
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                solve(sb, answer, area, i, j);
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static int search(int idx, int x, int y) {
        int size = 1;
        map[x][y] = idx;
        if (map[x + 1][y] == 0)
            size += search(idx, x + 1, y);
        if (map[x - 1][y] == 0)
            size += search(idx, x - 1, y);
        if (map[x][y + 1] == 0)
            size += search(idx, x, y + 1);
        if (map[x][y - 1] == 0)
            size += search(idx, x, y - 1);
        return size;
    }

    private static void solve(StringBuilder sb, int[][] ansMap, ArrayList<Integer> area, int i, int j) {
        if (map[i][j] == 1) {
            ansMap[i][j] += 1;
            if (map[i + 1][j] != 1)
                ansMap[i][j] += area.get(map[i + 1][j]);
            if (map[i][j + 1] != 1 && map[i + 1][j] != map[i][j + 1])
                ansMap[i][j] += area.get(map[i][j + 1]);
            if (map[i - 1][j] != 1 && map[i + 1][j] != map[i - 1][j] && map[i - 1][j] != map[i][j + 1])
                ansMap[i][j] += area.get(map[i - 1][j]);
            if (map[i][j - 1] != 1 && map[i][j - 1] != map[i + 1][j] && map[i][j - 1] != map[i][j + 1]
                    && map[i][j - 1] != map[i - 1][j])
                ansMap[i][j] += area.get(map[i][j - 1]);
        }
        sb.append(ansMap[i][j] % 10);
    }
}

/*
3 3
101
010
101

303
050
303


4 5
11001
00111
01010
10101

46003
00732
06040
50403
*/