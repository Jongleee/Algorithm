package com.example.algorithm.java.searchBroadFirstSearch;

import java.util.LinkedList;
import java.util.Queue;

public class CheckDistance {

    int[] dx = { 0, 1, 0, -1 };
    int[] dy = { -1, 0, 1, 0 };

    public int[] solution(String[][] places) {
        int[] result = new int[places.length];
        for (int i = 0; i < places.length; i++) {
            result[i] = isCorrext(places[i]);
        }
        return result;
    }

    public int isCorrext(String[] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length(); j++) {
                if (board[i].charAt(j) == 'P') {
                    if (!bfs(board, i, j))
                        return 0;
                }
            }
        }
        return 1;
    }

    static boolean bfs(String[] p, int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] { x, y });

        int[] dx = { 1, -1, 0, 0 };
        int[] dy = { 0, 0, 1, -1 };

        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = temp[0] + dx[i];
                int ny = temp[1] + dy[i];

                if ((nx < 0 || ny < 0 || nx >= 5 || ny >= 5) || (nx == x && ny == y)) {
                    continue;
                }

                int m = Math.abs(x - nx) + Math.abs(y - ny);

                if (p[nx].charAt(ny) == 'P' && m <= 2) {
                    return false;
                } else if (p[nx].charAt(ny) == 'O' && m < 2) {
                    queue.add(new int[] { nx, ny });
                }
            }
        }
        return true;
    }
}
