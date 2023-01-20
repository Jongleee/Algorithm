package com.example.algorithm.java.practice.graph;

public class BallMoveSimulation {
    public static long solution(int n, int m, int x, int y, int[][] queries) {
        long xMin = x;
        long yMin = y;
        long xMax = x;
        long yMax = y;
        for (int i = queries.length - 1; i >= 0; i--) {
            int direction = queries[i][0];
            int move = queries[i][1];
            switch (direction) {
                case 0:
                    yMin = findMin(yMin, move);
                    yMax = Math.min((long) m - 1, yMax + move);
                    break;
                case 1:
                    yMax = findMax(m, yMax, move);
                    yMin = Math.max(0, yMin - move);
                    break;
                case 2:
                    xMin = findMin(xMin, move);
                    xMax = Math.min((long) n - 1, xMax + move);
                    break;
                case 3:
                    xMax = findMax(n, xMax, move);
                    xMin = Math.max(0, xMin - move);
                    break;
                default:
                    break;
            }
            if (yMin >= m || yMax < 0 || xMin >= n || xMax < 0)
                return 0;
        }
        return (yMax - yMin + 1) * (xMax - xMin + 1);
    }

    private static long findMin(long min, int move) {
        if (min > 0)
            min += move;
        return min;
    }

    private static long findMax(int m, long max, int move) {
        if (max < m - 1)
            max -= move;
        return max;
    }

    public static void main(String[] args) {
        System.out.println(
                solution(2, 5, 0, 1, new int[][] { { 3, 1 }, { 2, 2 }, { 1, 1 }, { 2, 3 }, { 0, 1 }, { 2, 1 } }));
    }
    //답 2
}
