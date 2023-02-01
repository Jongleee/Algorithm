package com.example.algorithm.java.practice.searchBFS;

import java.util.LinkedList;
import java.util.Queue;

public class GameMapMinDistance {

    static int[] dx = { 1, 0, -1, 0 };
    static int[] dy = { 0, 1, 0, -1 };
    static Queue<int[]> queue = new LinkedList<>();

    public static int solution(int[][] maps) {
        int answer = 0;

        int[][] visited = new int[maps.length][maps[0].length];

        int x = 0;
        int y = 0;
        visited[x][y] = 1;
        queue.add(new int[] { x, y });
        bfs(maps, visited);
        answer = visited[maps.length - 1][maps[0].length - 1];

        if (answer == 0) {
            answer = -1;
        }

        return answer;
    }

    public static void bfs(int[][] maps, int[][] visited) {

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentX = current[0];
            int currentY = current[1];

            for (int i = 0; i < 4; i++) {
                int nX = currentX + dx[i];
                int nY = currentY + dy[i];

                if (nX < 0 || nX > maps.length - 1 || nY < 0 || nY > maps[0].length - 1)
                    continue;

                if (visited[nX][nY] == 0 && maps[nX][nY] == 1) {
                    visited[nX][nY] = visited[currentX][currentY] + 1;
                    queue.add(new int[] { nX, nY });
                }
            }

        }

    }

    public static void main(String[] args) {
        System.out.println(solution(new int[][] { { 1, 0, 1, 1, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 1, 1, 1 },
                { 1, 1, 1, 0, 1 }, { 0, 0, 0, 0, 1 } }));
        System.out.println(solution(new int[][] { { 1, 0, 1, 1, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 1, 1, 1 },
                { 1, 1, 1, 0, 0 }, { 0, 0, 0, 0, 1 } }));
        ;
    }
    // 11 , -1
}
