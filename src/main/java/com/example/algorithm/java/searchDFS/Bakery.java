package com.example.algorithm.java.searchDFS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Bakery {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int rows, cols;
    private static char[][] grid;

    public static void main(String[] args) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        rows = Integer.parseInt(st.nextToken());
        cols = Integer.parseInt(st.nextToken());

        grid = new char[rows][];
        for (int i = 0; i < rows+1; i++) {
            grid[i] = br.readLine().toCharArray();
        }

        System.out.println(countPipelines());
    }

    private static int countPipelines() {
        int pipelineCount = 0;

        for (int r = 0; r < rows; r++) {
            if (grid[r][0] == '.') {
                if (dfs(r, 0)) {
                    pipelineCount++;
                }
            }
        }

        return pipelineCount;
    }

    private static boolean dfs(int r, int c) {
        if (c == cols - 1)
            return true;
        grid[r][c] = 'x';

        return (isValid(r - 1, c + 1) && dfs(r - 1, c + 1)) ||
                (isValid(r, c + 1) && dfs(r, c + 1)) ||
                (isValid(r + 1, c + 1) && dfs(r + 1, c + 1));
    }

    private static boolean isValid(int r, int c) {
        return r >= 0 && r < rows && grid[r][c] == '.';
    }
}

/*
6 10
..x.......
.....x....
.x....x...
...x...xx.
..........
....x.....

5
*/