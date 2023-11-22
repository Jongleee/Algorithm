package com.example.algorithm.java.practice;

import java.util.ArrayList;
import java.util.List;

public class MakeStarAtIntersection {
    public String[] solution(int[][] line) {
        List<long[]> intersections = findIntersections(line);
        long minX = Long.MAX_VALUE;
        long maxX = Long.MIN_VALUE;
        long minY = Long.MAX_VALUE;
        long maxY = Long.MIN_VALUE;

        for (long[] intersection : intersections) {
            long x = intersection[0];
            long y = intersection[1];
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }

        boolean[][] answerTemp = moveAnswer(intersections, minX, maxX, minY, maxY);

        return makeAnswer(answerTemp);
    }

    private List<long[]> findIntersections(int[][] line) {
        List<long[]> intersections = new ArrayList<>();

        for (int i = 0; i < line.length; i++) {
            long a = line[i][0];
            long b = line[i][1];
            long e = line[i][2];

            for (int j = i + 1; j < line.length; j++) {
                long c = line[j][0];
                long d = line[j][1];
                long f = line[j][2];
                long xDown = a * d - b * c;

                if (xDown == 0) {
                    continue;
                }

                double x = (b * f - e * d) / (double) xDown;
                double y = (e * c - a * f) / (double) xDown;

                if (x == Math.ceil(x) && y == Math.ceil(y)) {
                    long xLong = (long) x;
                    long yLong = (long) y;
                    intersections.add(new long[] { xLong, yLong });
                }
            }
        }

        return intersections;
    }

    private boolean[][] moveAnswer(List<long[]> intersections, long minX, long maxX, long minY, long maxY) {
        boolean[][] answerTemp = new boolean[(int) (maxY - minY + 1)][(int) (maxX - minX + 1)];

        for (long[] intersection : intersections) {
            int x = (int) (intersection[0] - minX);
            int y = (int) (maxY - intersection[1]);
            answerTemp[y][x] = true;
        }

        return answerTemp;
    }

    private String[] makeAnswer(boolean[][] answerTemp) {
        String[] answer = new String[answerTemp.length];

        for (int i = 0; i < answer.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (boolean b : answerTemp[i]) {
                sb.append(b ? "*" : ".");
            }
            answer[i] = sb.toString();
        }

        return answer;
    }

    // @Test
    // void 정답() {
    //     int[][] l1 = { { 2, -1, 4 }, { -2, -1, 4 }, { 0, -1, 1 }, { 5, -8, -12 }, { 5, 8, 12 } };
    //     int[][] l2 = { { 0, 1, -1 }, { 1, 0, -1 }, { 1, 0, 1 } };
    //     Assertions.assertArrayEquals(new String[] { "....*....", ".........", ".........", "*.......*", ".........",
    //             ".........", ".........", ".........", "*.......*" }, solution(l1));
    //     Assertions.assertArrayEquals(new String[] { "*.*" }, solution(l2));
    // }
}
