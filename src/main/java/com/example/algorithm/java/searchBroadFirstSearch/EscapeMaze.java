package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.LinkedList;
import java.util.Queue;

public class EscapeMaze {
    static class Position {
        int x;
        int y;
        int level;

        public Position(int x, int y, int level) {
            this.x = x;
            this.y = y;
            this.level = level;
        }
    }

    char[][] map;
    boolean[][] visited;
    int[] dx = { -1, 0, 1, 0 };
    int[] dy = { 0, 1, 0, -1 };

    public int solution(String[] maps) {
        int row = maps.length;
        int col = maps[0].length();

        map = new char[row][col];
        visited = new boolean[row][col];

        Position startPosition = new Position(0, 0, 0);
        Position leverPosition = new Position(0, 0, 0);
        Position endPosition = new Position(0, 0, 0);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                char c = maps[i].charAt(j);

                if (c == 'S') {
                    startPosition = new Position(i, j, 0);
                }
                if (c == 'L') {
                    leverPosition = new Position(i, j, 0);
                }
                if (c == 'E') {
                    endPosition = new Position(i, j, 0);
                }

                map[i][j] = c;
            }
        }

        int answer = bfs(startPosition.x, startPosition.y, leverPosition.x, leverPosition.y, row, col);

        if (answer == -1) {
            return -1;
        }

        visited = new boolean[row][col];
        int temp = bfs(leverPosition.x, leverPosition.y, endPosition.x, endPosition.y, row, col);

        if (temp == -1) {
            return -1;
        }

        return answer + temp;
    }

    public int bfs(int startX, int startY, int endX, int endY, int height, int width) {
        Queue<Position> q = new LinkedList<>();
        q.add(new Position(startX, startY, 0));
        visited[startX][startY] = true;

        while (!q.isEmpty()) {
            Position now = q.poll();
            int x = now.x;
            int y = now.y;
            int level = now.level;

            if (x == endX && y == endY) {
                return level;
            }

            for (int i = 0; i < 4; i++) {
                int nowX = x + dx[i];
                int nowY = y + dy[i];

                if (nowX < 0 || nowX >= height || nowY < 0 || nowY >= width) {
                    continue;
                }

                if (!visited[nowX][nowY] && map[nowX][nowY] != 'X') {
                    q.add(new Position(nowX, nowY, level + 1));
                    visited[nowX][nowY] = true;
                }
            }
        }

        return -1;
    }

    // @Test
    // public void 정답() {
    //     String[] m1 = { "SOOOL", "XXXXO", "OOOOO", "OXXXX", "OOOOE" };
    //     String[] m2 = { "LOOXS", "OOOOX", "OOOOO", "OOOOO", "EOOOO" };
    //     Assertions.assertEquals(16, solution(m1));
    //     Assertions.assertEquals(-1, solution(m2));
    // }
}
