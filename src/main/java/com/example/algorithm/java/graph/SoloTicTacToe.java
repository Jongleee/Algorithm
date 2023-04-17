package com.example.algorithm.java.graph;


public class SoloTicTacToe {
    private static char[][] graph;
    private static int oCount;
    private static int xCount;

    public static int solution(String[] board) {
        init(board);
        if (xCount > oCount || oCount - xCount > 1) {
            return 0;
        }
        if (win('O') > 0 && win('X') > 0) {
            return 0;
        }
        if (win('O') > 0) {
            if (oCount == xCount) {
                return 0;
            }
        }
        if (win('X') > 0) {
            if (oCount > xCount) {
                return 0;
            }
        }
        return 1;
    }

    private static void init(String[] board) {
        graph = new char[3][3];
        oCount = 0;
        xCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                graph[i][j] = board[i].charAt(j);
                if (graph[i][j] == 'O') {
                    oCount++;
                }
                if (graph[i][j] == 'X') {
                    xCount++;
                }
            }
        }
    }

    public static int win(char c) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            if (graph[i][0] == c && graph[i][0] == graph[i][1] && graph[i][1] == graph[i][2]) {
                count++;
            }
            if (graph[0][i] == c && graph[0][i] == graph[1][i] && graph[1][i] == graph[2][i]) {
                count++;
            }
        }
        if (graph[0][0] == c && graph[0][0] == graph[1][1] && graph[1][1] == graph[2][2]) {
            count++;
        }
        if (graph[0][2] == c && graph[0][2] == graph[1][1] && graph[1][1] == graph[2][0]) {
            count++;
        }
        return count;
    }
    public static void main(String[] args) {
        System.out.println(solution(new String[] { "O.X", ".O.", "..X" }));
    }
}
