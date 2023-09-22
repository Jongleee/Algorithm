package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.LinkedList;
import java.util.Queue;

public class RicochetRobot {
    private static class Location {
        final int x;
        final int y;
        final int depth;

        public Location(int x, int y, int depth) {
            this.x = x;
            this.y = y;
            this.depth = depth;
        }
    }

    private static final int[] dx = { -1, 1, 0, 0 };
    private static final int[] dy = { 0, 0, -1, 1 };

    private int n;
    private int m;

    public int solution(String[] board) {
        initializeDimensions(board);

        Location robot = findLocation(board, 'R');
        Location goal = findLocation(board, 'G');

        return bfs(board, robot, goal);
    }

    private void initializeDimensions(String[] board) {
        n = board.length;
        m = board[0].length();
    }

    private Location findLocation(String[] board, char target) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char ch = board[i].charAt(j);

                if (ch == target) {
                    return new Location(i, j, 0);
                }
            }
        }
        return null;
    }

    private int bfs(String[] board, Location robot, Location goal) {
        Queue<Location> queue = new LinkedList<>();
        queue.add(robot);

        boolean[][] visited = new boolean[n][m];
        visited[robot.x][robot.y] = true;

        while (!queue.isEmpty()) {
            Location current = queue.poll();

            if (current.x == goal.x && current.y == goal.y) {
                return current.depth;
            }

            for (int i = 0; i < 4; i++) {
                int nx = current.x;
                int ny = current.y;

                while (inBounds(nx, ny) && board[nx].charAt(ny) != 'D') {
                    nx += dx[i];
                    ny += dy[i];
                }

                nx -= dx[i];
                ny -= dy[i];

                if (visited[nx][ny]) {
                    continue;
                }

                visited[nx][ny] = true;
                queue.add(new Location(nx, ny, current.depth + 1));
            }
        }

        return -1;
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < n && y < m;
    }
    // @Test
    // public void 정답() {
    //     String[] b1 = { "...D..R", ".D.G...", "....D.D", "D....D.", "..D...." };
    //     String[] b2 = { ".D.R", "....", ".G..", "...D" };
    //     Assertions.assertEquals(7, solution(b1));
    //     Assertions.assertEquals(-1, solution(b2));
    // }
}
