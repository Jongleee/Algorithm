package com.example.algorithm.java.searchBroadFirstSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Key {
    private static final int[][] DIRECTIONS = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

    private static class State {
        final int row, col;

        State(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int testCases = Integer.parseInt(br.readLine());
        while (testCases-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int rows = Integer.parseInt(st.nextToken());
            int cols = Integer.parseInt(st.nextToken());

            char[][] map = new char[rows + 2][cols + 2];
            for (int i = 1; i <= rows; i++) {
                char[] line = br.readLine().toCharArray();
                System.arraycopy(line, 0, map[i], 1, line.length);
            }

            int keys = getInitialKeys(br.readLine());
            sb.append(traverse(map, keys)).append('\n');
        }
        System.out.println(sb);
    }

    private static int getInitialKeys(String keyInput) {
        int keys = 0;
        if (!keyInput.equals("0")) {
            for (char ch : keyInput.toCharArray()) {
                keys |= 1 << (ch - 'a');
            }
        }
        return keys;
    }

    private static int traverse(char[][] map, int keys) {
        int rows = map.length;
        int cols = map[0].length;
        Queue<State> queue = new ArrayDeque<>();
        Queue<State> pendingQueue = new ArrayDeque<>();
        boolean[][] visited = new boolean[rows][cols];
        int documentCount = 0;
        boolean changed = true;

        queue.offer(new State(0, 0));

        while (changed) {
            changed = processQueue(map, keys, queue, pendingQueue);

            while (!queue.isEmpty()) {
                State current = queue.poll();

                for (int[] direction : DIRECTIONS) {
                    int newRow = current.row + direction[0];
                    int newCol = current.col + direction[1];

                    if (!isValidPosition(rows, cols, map, newRow, newCol) || visited[newRow][newCol]) {
                        continue;
                    }
                    visited[newRow][newCol] = true;

                    if (map[newRow][newCol] >= 'A' && map[newRow][newCol] <= 'Z') {
                        if ((keys & (1 << (map[newRow][newCol] - 'A'))) == 0) {
                            pendingQueue.offer(new State(newRow, newCol));
                            continue;
                        }
                    }

                    int temp = updateKeys(map, newRow, newCol, keys);
                    if (keys != temp) {
                        changed = true;
                        keys = temp;
                    }
                    if (map[newRow][newCol] == '$') {
                        documentCount++;
                    }
                    queue.offer(new State(newRow, newCol));
                }
            }
        }
        return documentCount;
    }

    private static boolean processQueue(char[][] map, int keys, Queue<State> queue, Queue<State> pendingQueue) {
        boolean changed = false;
        int size = pendingQueue.size();
        while (size-- > 0) {
            State state = pendingQueue.poll();
            int doorIndex = map[state.row][state.col] - 'A';
            if ((keys & (1 << doorIndex)) != 0) {
                queue.offer(state);
                changed = true;
            } else {
                pendingQueue.offer(state);
            }
        }
        return changed;
    }

    private static boolean isValidPosition(int rows, int cols, char[][] map, int row, int col) {
        return row >= 0 && col >= 0 && row < rows && col < cols && map[row][col] != '*';
    }

    private static int updateKeys(char[][] map, int row, int col, int keys) {
        char ch = map[row][col];
        if (ch >= 'a' && ch <= 'z') {
            return keys | (1 << (ch - 'a'));
        }
        return keys;
    }
}

/*
3
5 17
*****************
.............**$*
*B*A*P*C**X*Y*.X.
*y*x*a*p**$*$**$*
*****************
cz
5 11
*.*********
*...*...*x*
*X*.*.*.*.*
*$*...*...*
***********
0
7 7
*ABCDE*
X.....F
W.$$$.G
V.$$$.H
U.$$$.J
T.....K
*SQPML*
irony

3
1
0
*/