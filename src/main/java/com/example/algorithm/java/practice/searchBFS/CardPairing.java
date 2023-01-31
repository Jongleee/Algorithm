package com.example.algorithm.java.practice.searchBFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CardPairing {

    static ArrayList<int[]> orders;
    static int[] dr = { 1, -1, 0, 0 };
    static int[] dc = { 0, 0, 1, -1 };

    public static int solution(int[][] board, int r, int c) {
        int answer = Integer.MAX_VALUE;
        int n = 0;
        for (int[] row : board) {
            for (int e : row) {
                if (e != 0)
                    n++;
            }
        }
        n /= 2;
        int[] temp = new int[n];
        for (int i = 0; i < n; i++) {
            temp[i] = i + 1;
        }
        orders = new ArrayList<>();
        permutation(n, n, new int[n], temp, 0, 0);

        for (int[] order : orders) {
            int total = 0;
            int[] point = new int[] {r,c};
            int[][] cBoard = new int[4][4];// grid 만들기
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    cBoard[i][j] = board[i][j];
                }
            }
            for (int card : order) {
                int cnt = 0;
                cnt += bfs(cBoard, card, point) + 1;
                cBoard[point[0]][point[1]] = 0;
                cnt += bfs(cBoard, card, point) + 1;
                cBoard[point[0]][point[1]] = 0;

                total += cnt;
            }
            answer = Math.min(total, answer);
        }

        return answer;
    }

    private static int bfs(int[][] board, int target, int[] point) {
        int r = point[0];
        int c = point[1];
        Queue<int[]> que = new LinkedList<>();
        boolean[][] visited = new boolean[4][4];

        que.offer(new int[] { r, c, 0 });
        visited[r][c] = true;

        while (!que.isEmpty()) {
            int[] p = que.poll();
            if (board[p[0]][p[1]] == target) {
                point[0] = p[0];
                point[1] = p[1];
                return p[2];
            }
            standardMove(que, visited, p);

            controlMove(board, que, visited, p);
        }
        return 0;
    }

    private static void controlMove(int[][] board, Queue<int[]> que, boolean[][] visited, int[] p) {
        for (int d = 0; d < 4; d++) {
            int[] result = findCard(board, p[0], p[1], d);
            if ((result[0] != p[0] || result[1] != p[1]) && !visited[result[0]][result[1]]) {
                visited[result[0]][result[1]] = true;
                que.offer(new int[] { result[0], result[1], p[2] + 1 });
            }
        }
    }

    private static void standardMove(Queue<int[]> que, boolean[][] visited, int[] p) {
        for (int d = 0; d < 4; d++) {
            int nr = p[0] + dr[d];
            int nc = p[1] + dc[d];
            if (nr >= 0 && nr < 4 && nc >= 0 && nc < 4 && !visited[nr][nc]) {
                visited[nr][nc] = true;
                que.offer(new int[] { nr, nc, p[2] + 1 });
            }
        }
    }

    private static int[] findCard(int[][] board, int r, int c, int d) {
        r += dr[d];
        c += dc[d];
        while (r >= 0 && r < 4 && c >= 0 && c < 4) {
            if (board[r][c] != 0) {
                return new int[] { r, c };
            }
            r += dr[d];
            c += dc[d];
        }
        return new int[] { r - dr[d], c - dc[d] };
    }

    private static void permutation(int n, int r, int[] perms, int[] input, int depth, int flag) {
        if (depth == r) {
            int[] temp = new int[n];
            System.arraycopy(perms, 0, temp, 0, n);
            orders.add(temp);
            return;
        }
        for (int i = 0; i < n; i++) {
            if ((flag & (1 << i)) == 0) {
                perms[depth] = input[i];
                permutation(n, r, perms, input, depth + 1, flag | (1 << i));
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(
                solution(new int[][] { { 1, 0, 0, 3 }, { 2, 0, 0, 0 }, { 0, 0, 0, 2 }, { 3, 0, 1, 0 } }, 0, 0));
    }
}
