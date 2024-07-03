package com.example.algorithm.java.backtracking;

public class MoveCart {
    private static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final int MAX_STEPS = 999999;
    private static final int WALL = 5;
    private static final int RED_GOAL = 3;
    private static final int BLUE_GOAL = 4;

    private int[][] map;
    private boolean redReachedGoal;
    private boolean blueReachedGoal;
    private boolean[][][] visited;
    private final int[] dx = { -1, 1, 0, 0 };
    private final int[] dy = { 0, 0, -1, 1 };

    public int solution(int[][] maze) {
        initialize(maze);

        Point redStart = null;
        Point blueStart = null;

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                map[i][j] = maze[i][j];
                if (maze[i][j] == 1)
                    redStart = new Point(i, j);
                else if (maze[i][j] == 2)
                    blueStart = new Point(i, j);
            }
        }

        visited[redStart.x][redStart.y][0] = true;
        visited[blueStart.x][blueStart.y][1] = true;

        int steps = backtrack(redStart, blueStart, 0);
        return (steps == MAX_STEPS) ? 0 : steps;
    }

    private void initialize(int[][] maze) {
        map = new int[maze.length][maze[0].length];
        visited = new boolean[maze.length][maze[0].length][2];
    }

    private Point getNext(Point point, int dir) {
        int nx = point.x + dx[dir];
        int ny = point.y + dy[dir];
        return new Point(nx, ny);
    }

    private boolean isValidPosition(Point point) {
        return point.x >= 0 && point.x < map.length && point.y >= 0 && point.y < map[0].length
                && map[point.x][point.y] != WALL;
    }

    private boolean isPossibleMove(Point red, Point blue, Point curRed, Point curBlue) {
        if (red.x < 0 || red.y < 0 || red.x >= map.length || red.y >= map[0].length
                || blue.x < 0 || blue.y < 0 || blue.x >= map.length || blue.y >= map[0].length
                || map[red.x][red.y] == 5 || map[blue.x][blue.y] == 5)
            return false;
        if (!isValidPosition(red) || !isValidPosition(blue) || (red.x == blue.x && red.y == blue.y))
            return false;
        if ((red.x == curBlue.x && red.y == curBlue.y)
                && (blue.x == curRed.x && blue.y == curRed.y))
            return false;

        return !((!redReachedGoal && visited[red.x][red.y][0]) || (!blueReachedGoal && visited[blue.x][blue.y][1]));
    }

    private int backtrack(Point red, Point blue, int steps) {
        if ((redReachedGoal && blueReachedGoal))
            return steps;

        int minSteps = MAX_STEPS;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Point nextRed = !redReachedGoal ? getNext(red, i) : red;
                Point nextBlue = !blueReachedGoal ? getNext(blue, j) : blue;

                if (!isPossibleMove(nextRed, nextBlue, red, blue))
                    continue;

                visited[nextRed.x][nextRed.y][0] = true;
                visited[nextBlue.x][nextBlue.y][1] = true;

                if (map[nextRed.x][nextRed.y] == RED_GOAL)
                    redReachedGoal = true;
                if (map[nextBlue.x][nextBlue.y] == BLUE_GOAL)
                    blueReachedGoal = true;

                minSteps = Math.min(minSteps, backtrack(nextRed, nextBlue, steps + 1));

                redReachedGoal = false;
                blueReachedGoal = false;
                visited[nextRed.x][nextRed.y][0] = false;
                visited[nextBlue.x][nextBlue.y][1] = false;
            }
        }
        return minSteps;
    }

    // @Test
    // void 정답() {
    //     int[][][] maze = { { { 1, 4 }, { 0, 0 }, { 2, 3 } },
    //             { { 1, 0, 2 }, { 0, 0, 0 }, { 5, 0, 5 }, { 4, 0, 3 } },
    //             { { 1, 5 }, { 2, 5 }, { 4, 5 }, { 3, 5 } },
    //             { { 4, 1, 2, 3 } }
    //     };

    //     int[] result = { 3, 7, 0, 0 };

    //     for (int i = 0; i < result.length; i++) {
    //         Assertions.assertEquals(result[i], solution(maze[i]));
    //     }
    // }
}
