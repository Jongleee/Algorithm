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

    static char[][] map;
    static boolean[][] visited;
    static int[] dx = { -1, 0, 1, 0 };
    static int[] dy = { 0, 1, 0, -1 };

    public static int solution(String[] maps) {
        int answer = 0;
        int row = maps.length;
        int col = maps[0].length();

        map = new char[row][col];
        visited = new boolean[row][col];

        Position startPosition = null;
        Position leverPosition = null;
        Position endPosition = null;

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

        answer = bfs(startPosition.x, startPosition.y, leverPosition.x, leverPosition.y, maps.length, maps[0].length());

        return answer == -1 ? -1 : toEnd(maps, answer, leverPosition, endPosition);
    }

    private static int toEnd(String[] maps, int answer, Position leverPosition, Position endPosition) {
        visited = new boolean[map.length][map[0].length];
        int temp = bfs(leverPosition.x, leverPosition.y, endPosition.x, endPosition.y, maps.length, maps[0].length());

        if (temp == -1) {
            answer = -1;
        } else {
            answer += temp;
        }
        return answer;
    }

    public static int bfs(int startX, int startY, int endX, int endY, int height, int width) {
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

    public static void main(String[] args) {
        String[] m1 = { "SOOOL", "XXXXO", "OOOOO", "OXXXX", "OOOOE" };
        String[] m2 = { "LOOXS", "OOOOX", "OOOOO", "OOOOO", "EOOOO" };
        System.out.println(solution(m1));
        System.out.println(solution(m2));
    }

}
