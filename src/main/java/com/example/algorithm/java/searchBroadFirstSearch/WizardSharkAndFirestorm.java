package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class WizardSharkAndFirestorm {
    private static final int[] DR = { -1, 1, 0, 0 };
    private static final int[] DC = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        int len = (int) Math.pow(2, n);
        int[][] map = new int[len][len];
        boolean[][] visited = new boolean[len][len];

        for (int r = 0; r < len; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < len; c++) {
                map[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        int[] levels = new int[q];
        for (int i = 0; i < q; i++) {
            levels[i] = Integer.parseInt(st.nextToken());
        }

        for (int level : levels) {
            int size = (int) Math.pow(2, level);
            map = rotateSubgrids(map, size, len);
            map = meltIce(map, len);
        }

        int totalIce = calculateTotalIce(map);
        int largestBlock = findLargestIceBlock(map, visited, len);

        System.out.println(totalIce);
        System.out.println(largestBlock);
    }

    private static int[][] rotateSubgrids(int[][] map, int size, int len) {
        int[][] newMap = new int[len][len];

        for (int r = 0; r < len; r += size) {
            for (int c = 0; c < len; c += size) {
                rotate(map, newMap, r, c, size);
            }
        }
        return newMap;
    }

    private static void rotate(int[][] map, int[][] newMap, int r, int c, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newMap[r + j][c + size - 1 - i] = map[r + i][c + j];
            }
        }
    }

    private static int[][] meltIce(int[][] map, int len) {
        int[][] newMap = new int[len][len];

        for (int i = 0; i < len; i++) {
            newMap[i] = Arrays.copyOf(map[i], len);
        }

        for (int r = 0; r < len; r++) {
            for (int c = 0; c < len; c++) {
                if (map[r][c] == 0)
                    continue;
                int adjacentIce = countAdjacentIce(map, r, c, len);
                if (adjacentIce < 3) {
                    newMap[r][c] = Math.max(0, map[r][c] - 1);
                }
            }
        }
        return newMap;
    }

    private static int countAdjacentIce(int[][] map, int r, int c, int len) {
        int count = 0;
        for (int d = 0; d < 4; d++) {
            int nr = r + DR[d];
            int nc = c + DC[d];
            if (isInBounds(nr, nc, len) && map[nr][nc] > 0) {
                count++;
            }
        }
        return count;
    }

    private static int calculateTotalIce(int[][] map) {
        int total = 0;
        for (int[] row : map) {
            for (int cell : row) {
                total += cell;
            }
        }
        return total;
    }

    private static int findLargestIceBlock(int[][] map, boolean[][] visited, int len) {
        int largestBlock = 0;

        for (int r = 0; r < len; r++) {
            for (int c = 0; c < len; c++) {
                if (map[r][c] > 0 && !visited[r][c]) {
                    largestBlock = Math.max(largestBlock, bfs(map, visited, r, c, len));
                }
            }
        }
        return largestBlock;
    }

    private static int bfs(int[][] map, boolean[][] visited, int r, int c, int len) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] { r, c });
        visited[r][c] = true;
        int count = 1;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int cr = curr[0], cc = curr[1];

            for (int d = 0; d < 4; d++) {
                int nr = cr + DR[d];
                int nc = cc + DC[d];
                if (isInBounds(nr, nc, len) && map[nr][nc] > 0 && !visited[nr][nc]) {
                    queue.add(new int[] { nr, nc });
                    visited[nr][nc] = true;
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isInBounds(int r, int c, int len) {
        return r >= 0 && c >= 0 && r < len && c < len;
    }
}

/*
3 10
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8
8 7 6 5 4 3 2 1
1 2 0 3 2 1 2 3 2 3

248
62


3 10
1 0 3 4 5 6 7 0
8 0 6 5 4 3 2 1
1 2 0 4 5 6 7 0
8 7 6 5 4 3 2 1
1 2 3 4 0 6 7 0
8 7 0 5 4 3 2 1
1 2 3 4 5 6 7 0
0 7 0 5 4 3 2 1
1 2 3 1 2 3 1 2 3 1

37
9
*/