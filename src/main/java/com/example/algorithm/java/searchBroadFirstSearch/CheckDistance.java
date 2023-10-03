package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.LinkedList;
import java.util.Queue;

public class CheckDistance {
    private static final int[] dx = { 0, 1, 0, -1 };
    private static final int[] dy = { -1, 0, 1, 0 };

    public int[] solution(String[][] places) {
        int[] result = new int[places.length];
        for (int i = 0; i < places.length; i++) {
            result[i] = isCorrect(places[i]);
        }
        return result;
    }

    private int isCorrect(String[] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length(); j++) {
                if (board[i].charAt(j) == 'P') {
                    if (!isSafe(board, i, j))
                        return 0;
                }
            }
        }
        return 1;
    }

    private boolean isSafe(String[] p, int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] { x, y });
        boolean[][] visited = new boolean[p.length][p[0].length()];

        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int currX = temp[0];
            int currY = temp[1];

            visited[currX][currY] = true;

            for (int i = 0; i < 4; i++) {
                int nx = currX + dx[i];
                int ny = currY + dy[i];

                if (!isValid(p, nx, ny) || visited[nx][ny]) {
                    continue;
                }

                int manhattanDistance = Math.abs(x - nx) + Math.abs(y - ny);

                if (p[nx].charAt(ny) == 'P' && manhattanDistance <= 2) {
                    return false;
                } else if (p[nx].charAt(ny) == 'O' && manhattanDistance < 2) {
                    queue.add(new int[] { nx, ny });
                }
            }
        }
        return true;
    }

    private boolean isValid(String[] p, int x, int y) {
        return x >= 0 && y >= 0 && x < p.length && y < p[0].length();
    }

    // @Test
    // public void 정답() {
    //     String[][] p1 = { { "POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP" },
    //             { "POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP" }, { "PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX" },
    //             { "OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO" }, { "PXPXP", "XPXPX", "PXPXP", "XPXPX", "PXPXP" } };
    //     Assertions.assertArrayEquals(new int[] { 1, 0, 1, 1, 1 }, solution(p1));
    // }
}
