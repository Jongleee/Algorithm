package com.example.algorithm.java.practice.searchBFS;

import java.util.LinkedList;
import java.util.Queue;

public class MovingBlock {
    static class Robot {
        int x1;
        int x2;
        int y1;
        int y2;
        int time;
        int vertical;

        Robot(int x1, int y1, int x2, int y2, int time, int v) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.time = time;
            this.vertical = v;
        }
    }

    public static int solution(int[][] board) {
        int answer = 0;
        Queue<Robot> q = new LinkedList<>();
        int[][] op = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        boolean[][][] visited = new boolean[board.length][board.length][2];

        q.offer(new Robot(0, 0, 0, 1, 0, 0));

        while (!q.isEmpty()) {
            Robot robot = q.peek();
            q.poll();

            if (robot.x1 < 0 || robot.x1 >= board.length || robot.y1 < 0 || robot.y1 >= board.length ||
                    robot.x2 < 0 || robot.x2 >= board.length || robot.y2 < 0 || robot.y2 >= board.length) {
                continue;
            }

            if (board[robot.x1][robot.y1] == 1 || board[robot.x2][robot.y2] == 1) {
                continue;
            }

            if (visited[robot.x1][robot.y1][robot.vertical] &&
                    visited[robot.x2][robot.y2][robot.vertical])
                continue;

            if ((robot.x1 == board.length - 1 && robot.y1 == board.length - 1) ||
                    (robot.x2 == board.length - 1 && robot.y2 == board.length - 1)) {
                answer = robot.time;
                break;
            }

            visited[robot.x1][robot.y1][robot.vertical] = true;
            visited[robot.x2][robot.y2][robot.vertical] = true;

            nextCoordinate(q, op, robot);

            moving(board, q, robot);
        }

        return answer;
    }

    private static void nextCoordinate(Queue<Robot> q, int[][] op, Robot item) {
        for (int i = 0; i < op.length; i++) {
            int nextX1 = item.x1 + op[i][0];
            int nextY1 = item.y1 + op[i][1];
            int nextX2 = item.x2 + op[i][0];
            int nextY2 = item.y2 + op[i][1];

            q.offer(new Robot(nextX1, nextY1, nextX2, nextY2, item.time + 1, item.vertical));
        }
    }

    private static void moving(int[][] board, Queue<Robot> q, Robot item) {
        if (item.vertical == 1) {
            if (item.y1 - 1 >= 0 && board[item.x1][item.y1 - 1] == 0 && board[item.x2][item.y2 - 1] == 0) {
                q.offer(new Robot(item.x1, item.y1, item.x1, item.y1 - 1, item.time + 1, 0));
                q.offer(new Robot(item.x2, item.y2, item.x2, item.y2 - 1, item.time + 1, 0));
            }

            if (item.y1 + 1 <= (board.length - 1) &&
                    board[item.x1][item.y1 + 1] == 0 && board[item.x2][item.y2 + 1] == 0) {
                q.offer(new Robot(item.x1, item.y1, item.x1, item.y1 + 1, item.time + 1, 0));
                q.offer(new Robot(item.x2, item.y2, item.x2, item.y2 + 1, item.time + 1, 0));
            }
        } else {
            if (item.x1 - 1 >= 0 && board[item.x1 - 1][item.y1] == 0 &&
                    board[item.x2 - 1][item.y2] == 0) {
                q.offer(new Robot(item.x1, item.y1, item.x1 - 1, item.y1, item.time + 1, 1));
                q.offer(new Robot(item.x2, item.y2, item.x2 - 1, item.y2, item.time + 1, 1));
            }

            if (item.x1 + 1 <= (board.length - 1) && board[item.x1 + 1][item.y1] == 0 &&
                    board[item.x2 + 1][item.y2] == 0) {
                q.offer(new Robot(item.x1, item.y1, item.x1 + 1, item.y1, item.time + 1, 1));
                q.offer(new Robot(item.x2, item.y2, item.x2 + 1, item.y2, item.time + 1, 1));
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{0, 0, 0, 1, 1},{0, 0, 0, 1, 0},{0, 1, 0, 1, 1},{1, 1, 0, 0, 1},{0, 0, 0, 0, 0}}));
    }
    //답 7
}
