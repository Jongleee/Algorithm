package com.example.algorithm.java.bruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Laboratory {
    private static final int EMPTY = -1;
    private static final int WALL = 0;
    private static final int VIRUS = 1;

    private static int n, m, size, max;
    private static int[] map;
    private static ArrayList<Integer> viruses;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        size = m + 2;
        max = (n + 2) * (m + 2);

        map = new int[max];
        viruses = new ArrayList<>();
        int totalEmpty = initializeMap(br);

        int maxSafeArea = calculateMaxSafeArea(totalEmpty);
        System.out.print(maxSafeArea);
    }

    private static int initializeMap(BufferedReader br) throws IOException {
        int emptyCount = 0;
        StringTokenizer st;
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= m; j++) {
                int pos = i * size + j;
                map[pos] = Integer.parseInt(st.nextToken()) - 1;
                if (map[pos] == EMPTY || map[pos] == VIRUS) {
                    emptyCount++;
                    if (map[pos] == VIRUS) {
                        viruses.add(pos);
                    }
                }
            }
        }
        return emptyCount;
    }

    private static int calculateMaxSafeArea(int totalEmpty) {
        int result = 0;
        boolean[] visited = new boolean[max];
        boolean[] copy = new boolean[max];
        int range = max - size - 1;
        totalEmpty -= 3;

        for (int i = size + 1; i < range; i++) {
            if (map[i] != EMPTY)
                continue;
            map[i] = WALL;
            for (int j = i + 1; j < range; j++) {
                if (map[j] != EMPTY)
                    continue;
                map[j] = WALL;
                for (int k = j + 1; k < range; k++) {
                    if (map[k] != EMPTY)
                        continue;
                    map[k] = WALL;

                    result = Math.max(result, totalEmpty - infect(copy, visited));

                    map[k] = EMPTY;
                }
                map[j] = EMPTY;
            }
            map[i] = EMPTY;
        }
        return result;
    }

    private static int infect(boolean[] copy, boolean[] visited) {
        int infectedCount = 0;
        System.arraycopy(copy, 0, visited, 0, max);

        for (int virus : viruses) {
            dfs(virus, visited);
        }

        for (int i = 0; i < max; i++) {
            if (visited[i])
                infectedCount++;
        }
        return infectedCount;
    }

    private static void dfs(int pos, boolean[] visited) {
        if (visited[pos] || map[pos] == WALL)
            return;

        visited[pos] = true;
        dfs(pos - size, visited);
        dfs(pos + size, visited);
        dfs(pos - 1, visited);
        dfs(pos + 1, visited);
    }
}

/*
7 7
2 0 0 0 1 1 0
0 0 1 0 1 2 0
0 1 1 0 1 0 0
0 1 0 0 0 0 0
0 0 0 0 0 1 1
0 1 0 0 0 0 0
0 1 0 0 0 0 0

27


4 6
0 0 0 0 0 0
1 0 0 0 0 2
1 1 1 0 0 2
0 0 0 0 0 2

9
*/