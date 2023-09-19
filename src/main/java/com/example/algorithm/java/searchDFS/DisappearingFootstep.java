package com.example.algorithm.java.searchDFS;

public class DisappearingFootstep {

    final int INF = 987654321;

    public int solution(int[][] board, int[] aloc, int[] bloc) {
        return solve(board, aloc[0], aloc[1], bloc[0], bloc[1])[1];
    }

    public int[] solve(int[][] board, int y1, int x1, int y2, int x2) {
        if (isFinished(board, y1, x1)) {
            return new int[] { 0, 0 };
        }

        if (y1 == y2 && x1 == x2) {
            return new int[] { 1, 1 };
        }

        int minTurn = INF;
        int maxTurn = 0;
        int canWin = 0;

        int[] dy = { -1, 1, 0, 0 };
        int[] dx = { 0, 0, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int ny = y1 + dy[i];
            int nx = x1 + dx[i];
            if (!inRange(board, ny, nx) || board[ny][nx] == 0) {
                continue;
            }

            board[y1][x1] = 0;
            int[] result = solve(board, y2, x2, ny, nx);
            board[y2][x2] = 1;

            if (result[0] == 0) {
                canWin = 1;
                minTurn = Math.min(minTurn, result[1]);
            } else if (canWin != 1) {
                maxTurn = Math.max(maxTurn, result[1]);
            }
        }

        int turn = canWin == 1 ? minTurn : maxTurn;

        return new int[] { canWin, turn + 1 };
    }

    public boolean inRange(int[][] board, int y, int x) {
        return y >= 0 && y < board.length && x >= 0 && x < board[0].length;
    }

    public boolean isFinished(int[][] board, int y, int x) {
        int[] dy = { -1, 1, 0, 0 };
        int[] dx = { 0, 0, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];
            if (inRange(board, ny, nx) && board[ny][nx] != 0) {
                return false;
            }
        }

        return true;
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(5, solution(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, new int[] { 1, 0 }, new int[] { 1, 2 }));
    //     Assertions.assertEquals(4, solution(new int[][] { { 1, 1, 1 }, { 1, 0, 1 }, { 1, 1, 1 } }, new int[] { 1, 0 }, new int[] { 1, 2 }));
    // }
}
