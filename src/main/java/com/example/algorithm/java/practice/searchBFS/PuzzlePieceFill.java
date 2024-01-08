package com.example.algorithm.java.practice.searchBFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PuzzlePieceFill {
    class Point implements Comparable<Point> {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point o) {
            if (this.x == o.x) {
                return this.y - o.y;
            }

            return this.x - o.x;
        }
    }

    private static final int[] dx = { -1, 0, 1, 0 };
    private static final int[] dy = { 0, -1, 0, 1 };

    private int boardSize;
    private List<List<Point>> empty;
    private List<List<Point>> block;
    private boolean[][] visited;

    public int solution(int[][] gameBoard, int[][] table) {
        empty = new ArrayList<>();
        block = new ArrayList<>();
        boardSize = gameBoard.length;
        visited = new boolean[boardSize][boardSize];

        init(gameBoard, table);

        boolean[] visitedBoard = new boolean[empty.size()];
        int answer = 0;

        for (List<Point> currentBlock : block) {
            for (int j = 0; j < empty.size(); j++) {
                List<Point> currentEmpty = empty.get(j);
                if (currentEmpty.size() == currentBlock.size() && !visitedBoard[j]) {
                    if (isRotate(currentEmpty, currentBlock)) {
                        answer += currentBlock.size();
                        visitedBoard[j] = true;
                        break;
                    }
                }
            }
        }

        return answer;
    }

    private void init(int[][] gameBoard, int[][] table) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (gameBoard[i][j] == 0 && !visited[i][j]) {
                    empty.add(check(gameBoard, i, j, 0));
                }
            }
        }

        for (int i = 0; i < boardSize; i++) {
            Arrays.fill(visited[i], false);
        }

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (table[i][j] == 1 && !visited[i][j])
                    block.add(check(table, i, j, 1));
            }
        }
    }

    public List<Point> check(int[][] board, int x, int y, int type) {
        Queue<Point> q = new LinkedList<>();
        ArrayList<Point> result = new ArrayList<>();

        q.add(new Point(x, y));
        visited[x][y] = true;

        result.add(new Point(0, 0));

        while (!q.isEmpty()) {
            Point current = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                if (nx >= 0 && nx < boardSize && ny >= 0 && ny < boardSize) {
                    if (!visited[nx][ny] && board[nx][ny] == type) {
                        q.add(new Point(nx, ny));
                        visited[nx][ny] = true;
                        result.add(new Point(nx - x, ny - y));
                    }
                }

            }
        }

        Collections.sort(result);

        return result;
    }

    public boolean isRotate(List<Point> empty, List<Point> block) {
        for (int i = 0; i < 4; i++) {
            int zeroX = block.get(0).x;
            int zeroY = block.get(0).y;

            for (int j = 0; j < block.size(); j++) {
                block.get(j).x -= zeroX;
                block.get(j).y -= zeroY;
            }

            boolean isCollect = true;

            for (int j = 0; j < empty.size(); j++) {
                Point emptyPoint = empty.get(j);
                Point blockPoint = block.get(j);

                if (emptyPoint.x != blockPoint.x || emptyPoint.y != blockPoint.y) {
                    isCollect = false;
                    break;
                }
            }

            if (isCollect) {
                return true;
            } else {
                for (int j = 0; j < block.size(); j++) {
                    int temp = block.get(j).x;

                    block.get(j).x = block.get(j).y;
                    block.get(j).y = -temp;
                }

                Collections.sort(block);
            }

        }

        return false;
    }

    // @Test
    // void 정답() {
    //     int[][] g1 = { { 1, 1, 0, 0, 1, 0 }, { 0, 0, 1, 0, 1, 0 }, { 0, 1, 1, 0, 0, 1 }, { 1, 1, 0, 1, 1, 1 },
    //             { 1, 0, 0, 0, 1, 0 }, { 0, 1, 1, 1, 0, 0 } };
    //     int[][] t1 = { { 1, 0, 0, 1, 1, 0 }, { 1, 0, 1, 0, 1, 0 }, { 0, 1, 1, 0, 1, 1 }, { 0, 0, 1, 0, 0, 0 },
    //             { 1, 1, 0, 1, 1, 0 }, { 0, 1, 0, 0, 0, 0 } };

    //     int[][] g2 = { { 0, 0, 0 }, { 1, 1, 0 }, { 1, 1, 1 } };
    //     int[][] t2 = { { 1, 1, 1 }, { 1, 0, 0 }, { 0, 0, 0 } };

    //     Assertions.assertEquals(14, solution(g1, t1));
    //     Assertions.assertEquals(0, solution(g2, t2));
    // }
}
