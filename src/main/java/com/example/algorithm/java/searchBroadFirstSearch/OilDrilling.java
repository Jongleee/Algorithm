package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class OilDrilling {
    private static final int[] dx = new int[] { 0, 1, 0, -1 };
    private static final int[] dy = new int[] { -1, 0, 1, 0 };
    private Map<Integer, Integer> answerMap;

    public int solution(int[][] land) {
        answerMap = new HashMap<>();
        for (int i = 0; i < land.length; i++) {
            for (int j = 0; j < land[i].length; j++) {
                if (land[i][j] == 1) {
                    Set<Integer> set = new HashSet<>();
                    bfs(land, new int[] { i, j }, set);
                }
            }
        }
        return answerMap.values()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }

    private void bfs(int[][] map, int[] now, Set<Integer> set) {
        int size = 1;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(now);
        set.add(now[1]);
        map[now[0]][now[1]] = 2;
        while (!queue.isEmpty()) {
            int[] next = queue.poll();
            int x = next[0];
            int y = next[1];
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx >= 0 && nx < map.length && ny >= 0 && ny < map[0].length && map[nx][ny] == 1) {
                    map[nx][ny] = 2;
                    queue.add(new int[] { nx, ny });
                    set.add(ny);
                    size++;
                }
            }
        }
        for (int y : set) {
            answerMap.put(y, answerMap.getOrDefault(y, 0) + size);
        }
    }

    // @Test
    // void 정답() {
    //     int[][][] land = {
    //             { { 0, 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 0, 1, 1, 0, 0 }, { 1, 1, 0, 0, 0, 1, 1, 0 },
    //                     { 1, 1, 1, 0, 0, 0, 0, 0 }, { 1, 1, 1, 0, 0, 0, 1, 1 } },
    //             { { 1, 0, 1, 0, 1, 1 }, { 1, 0, 1, 0, 0, 0 }, { 1, 0, 1, 0, 0, 1 }, { 1, 0, 0, 1, 0, 0 },
    //                     { 1, 0, 0, 1, 0, 1 }, { 1, 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1, 1 } } };

    //     int[] result = { 9, 16 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(land[i]));
    //     }
    // }
}
