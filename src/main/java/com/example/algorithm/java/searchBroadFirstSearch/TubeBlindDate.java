package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.ArrayDeque;
import java.util.Queue;

public class TubeBlindDate {
    static int[] dx = { -1, 1, 0, 0 };
    static int[] dy = { 0, 0, -1, 1 };

    public int[] solution(int m, int n, int s, int[][] timeMap) {
        int[][] moveCountMap = new int[m][n];
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[] { 0, 0 });

        long[][] dist = new long[m][n];
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                dist[i][j] = Long.MAX_VALUE;
        dist[0][0] = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];

            if (x == m - 1 && y == n - 1) {
                if (dist[x][y] <= s)
                    break;
            }
            for (int d = 0; d < 4; ++d) {
                int nextX = x + dx[d];
                int nextY = y + dy[d];

                if (0 <= nextX && nextX < m && 0 <= nextY && nextY < n) {
                    if (timeMap[nextX][nextY] == -1)
                        continue;
                    if (dist[nextX][nextY] > dist[x][y] + timeMap[nextX][nextY]) {
                        dist[nextX][nextY] = dist[x][y] + timeMap[nextX][nextY];
                        q.add(new int[] { nextX, nextY });
                        moveCountMap[nextX][nextY] = moveCountMap[x][y] + 1;
                    }
                }
            }
        }

        int[] answer = new int[2];
        answer[0] = moveCountMap[m - 1][n - 1];
        answer[1] = (int) dist[m - 1][n - 1];

        return answer;
    }

    // @Test
    // void 정답() {
    //     int[] m = { 3, 4, 5 };
    //     int[] n = { 3, 6, 5 };
    //     int[] s = { 150, 25, 12 };
    //     int[][][] timeMap = { { { 0, 2, 99 }, { 100, 100, 4 }, { 1, 2, 0 } },
    //             { { 0, 1, 1, -1, 2, 4 }, { -1, 7, 2, 1, 5, 7 }, { -1, 1, -1, 1, 6, 3 }, { -1, 1, -1, -1, 7, 0 } },
    //             { { 0, 1, 1, 1, 1 }, { 9, 9, 9, 1, 9 }, { 1, 1, 1, 1, 9 }, { 1, 1, 5, 9, 9 }, { 1, 1, 1, 1, 0 } } };
    //     int[][] result = { { 4, 103 }, { 8, 15 }, { 12, 11 } };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertArrayEquals(result[i], solution(m[i], n[i], s[i], timeMap[i]));
    //     }
    // }
}
