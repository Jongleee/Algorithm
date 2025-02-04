package com.example.algorithm.java.implementation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DiceRolling2 {
    static int[] dx = { -1, 0, 1, 0 };
    static int[] dy = { 0, 1, 0, -1 };

    static class Dice {
        private int top, bottom, front, back, left, right, x, y;

        public Dice() {
            top = 1;
            bottom = 6;
            front = 5;
            back = 2;
            left = 4;
            right = 3;
            x = 0;
            y = 0;
        }

        public void move(int direction) {
            int temp;
            switch (direction) {
                case 0:
                    temp = top;
                    top = front;
                    front = bottom;
                    bottom = back;
                    back = temp;
                    break;
                case 1:
                    temp = top;
                    top = left;
                    left = bottom;
                    bottom = right;
                    right = temp;
                    break;
                case 2:
                    temp = top;
                    top = back;
                    back = bottom;
                    bottom = front;
                    front = temp;
                    break;
                case 3:
                    temp = top;
                    top = right;
                    right = bottom;
                    bottom = left;
                    left = temp;
                    break;
                default:
                    break;
            }
            x += dx[direction];
            y += dy[direction];
        }

        public int getBottom() {
            return bottom;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int n = Integer.parseInt(tokenizer.nextToken());
        int m = Integer.parseInt(tokenizer.nextToken());
        int k = Integer.parseInt(tokenizer.nextToken());

        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        int[][] scoreMap = calculateScores(n, m, map);
        System.out.println(simulateGame(n, m, k, map, scoreMap));
    }

    private static int[][] calculateScores(int n, int m, int[][] map) {
        int[][] scoreMap = new int[n][m];
        boolean[][] visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (visited[i][j])
                    continue;
                bfs(map, scoreMap, visited, i, j, n, m);
            }
        }
        return scoreMap;
    }

    private static void bfs(int[][] map, int[][] scoreMap, boolean[][] visited, int x, int y, int n, int m) {
        int num = map[x][y];
        int count = 1;
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        List<int[]> positions = new ArrayList<>();

        queue.offer(new int[] { x, y });
        visited[x][y] = true;
        positions.add(new int[] { x, y });

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            for (int d = 0; d < 4; d++) {
                int nx = pos[0] + dx[d], ny = pos[1] + dy[d];
                if (nx >= 0 && nx < n && ny >= 0 && ny < m && !visited[nx][ny] && map[nx][ny] == num) {
                    visited[nx][ny] = true;
                    queue.offer(new int[] { nx, ny });
                    positions.add(new int[] { nx, ny });
                    count++;
                }
            }
        }
        for (int[] pos : positions) {
            scoreMap[pos[0]][pos[1]] = count * num;
        }
    }

    private static int simulateGame(int n, int m, int k, int[][] map, int[][] scoreMap) {
        Dice dice = new Dice();
        int direction = 1, score = 0;

        for (int i = 0; i < k; i++) {
            direction = moveAndAdjustDirection(dice, direction, n, m);
            score += scoreMap[dice.getX()][dice.getY()];
            direction = adjustDirection(map[dice.getX()][dice.getY()], dice.getBottom(), direction);
        }
        return score;
    }

    private static int moveAndAdjustDirection(Dice dice, int direction, int n, int m) {
        int nx = dice.getX() + dx[direction], ny = dice.getY() + dy[direction];
        if (nx < 0 || nx >= n || ny < 0 || ny >= m) {
            direction = (direction + 2) % 4;
        }
        dice.move(direction);
        return direction;
    }

    private static int adjustDirection(int mapValue, int diceBottom, int direction) {
        if (diceBottom > mapValue)
            return (direction + 1) % 4;
        if (diceBottom < mapValue)
            return (direction + 3) % 4;
        return direction;
    }
}

/*
4 5 1
4 1 2 3 3
6 1 1 3 3
5 6 1 3 2
5 5 6 5 5

4


4 5 5
4 1 2 3 3
6 1 1 3 3
5 6 1 3 2
5 5 6 5 5

24


4 5 1000
4 1 2 3 3
6 1 1 3 3
5 6 1 3 2
5 5 6 5 5

3901
*/