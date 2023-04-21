package com.example.algorithm.java.practice;

import java.util.ArrayList;
import java.util.List;

public class MakeStarAtIntersection {
    public String[] solution(int[][] line) {
        List<long[]> intersections = new ArrayList<>();
        long minX = Long.MAX_VALUE;
        long maxX = Long.MIN_VALUE;
        long minY = Long.MAX_VALUE;
        long maxY = Long.MIN_VALUE;

        for (int i = 0; i < line.length; i++) {
            long a = line[i][0];
            long b = line[i][1];
            long e = line[i][2];

            for (int j = i + 1; j < line.length; j++) {
                long c = line[j][0];
                long d = line[j][1];
                long f = line[j][2];
                long temp = a * d - b * c;

                if (temp == 0)
                    continue;

                double x = (b * f - e * d) / (double) temp;
                double y = (e * c - a * f) / (double) temp;

                if (x == Math.ceil(x) && y == Math.ceil(y)) {
                    long xLong = (long) x;
                    long yLong = (long) y;
                    intersections.add(new long[] { xLong, yLong });
                    minX = Math.min(minX, xLong);
                    maxX = Math.max(maxX, xLong);
                    minY = Math.min(minY, yLong);
                    maxY = Math.max(maxY, yLong);
                }
            }
        }

        boolean[][] answerTemp = moveAnswer(intersections, minX, maxX, minY, maxY);

        return makeAnswer(answerTemp);
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
                if (b) {
                    sb.append("*");
                    continue;
                } 
                    sb.append(".");
            }
            answer[i] = sb.toString();
        }
        return answer;
    }
}
