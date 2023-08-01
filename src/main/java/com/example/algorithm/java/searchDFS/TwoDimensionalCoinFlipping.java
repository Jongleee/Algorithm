package com.example.algorithm.java.searchDFS;

public class TwoDimensionalCoinFlipping {
    private int n;
    private int m;
    private int[][] board;
    private int[][] t;
    private int answer;

    public int solution(int[][] beginning, int[][] target) {
        answer = Integer.MAX_VALUE;
        n = beginning.length;
        m = beginning[0].length;

        board = beginning;
        t = target;

        dfs(0, 0);

        return (answer == Integer.MAX_VALUE) ? -1 : answer;
    }

    public void flipRow(int r) {
        for (int i = 0; i < m; i++) {
            board[r][i] = (board[r][i] + 1) % 2;
        }
    }

    public int compareCol(int c) {
        int check = 0;
        for (int i = 0; i < n; i++) {
            if (t[i][c] == board[i][c]) {
                check++;
            }
        }

        if (check == n) {
            return 0;
        } else if (check == 0) {
            return 1;
        }

        return -1;
    }

    public void dfs(int r, int cnt) {
        if (r == n) {
            boolean flag = true;
            for (int i = 0; i < m; i++) {
                int result = compareCol(i);
                if (result == -1) {
                    flag = false;
                    break;
                }
                cnt += result;
            }

            if (flag) {
                answer = Math.min(answer, cnt);
            }
            return;
        }

        flipRow(r);
        dfs(r + 1, cnt + 1);
        flipRow(r);

        dfs(r + 1, cnt);
    }

    // @Test
    // public void 정답() {
    //     Assertions.assertEquals(5,
    //             solution(
    //                     new int[][] { { 0, 1, 0, 0, 0 }, { 1, 0, 1, 0, 1 }, { 0, 1, 1, 1, 0 }, { 1, 0, 1, 1, 0 },
    //                             { 0, 1, 0, 1, 0 } },
    //                     new int[][] { { 0, 0, 0, 1, 1 }, { 0, 0, 0, 0, 1 }, { 0, 0, 1, 0, 1 }, { 0, 0, 0, 1, 0 },
    //                             { 0, 0, 0, 0, 1 } }));
    //     Assertions.assertEquals(-1,
    //             solution(new int[][] { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } },
    //                     new int[][] { { 1, 0, 1 }, { 0, 0, 0 }, { 0, 0, 0 } }));
    // }
}
